# -*- coding: UTF-8 -*-
'''
Created on 2018年2月1日

@author: xiaoJ
'''
from collections import namedtuple
from ansible.parsing.dataloader import DataLoader
from ansible.vars.manager import VariableManager
from ansible.inventory.manager import InventoryManager
from ansible.playbook.play import Play
from ansible.executor.task_queue_manager import TaskQueueManager
import ResultCallback

class Runner:
    '''
    用于执行脚本公共处理类
    '''
    
    def __init__(self):
        '''
        Constructor
        '''
        Options = namedtuple('Options', ['connection', 'module_path', 'forks', 'become', 'become_method', 'become_user', 'check', 'diff'])
        # initialize needed objects
        self._loader = DataLoader()
        self._options = Options(connection='smart', module_path='shell', forks=100, become=None, become_method=None, become_user=None, check=False,
                          diff=False)
        # Instantiate our ResultCallback for handling results as they come in
        self._results_callback = ResultCallback.ResultCallback()
        
        # create inventory and pass to var manager
        self._inventory = InventoryManager(loader = self._loader, sources=['/ansible/demo/flask/host_lit'])
        self._variable_manager = VariableManager(loader=self._loader, inventory=self._inventory)
        self._passwords = dict(vault_pass='secret')
        
    #执行脚本    
    def run(self):
        # actually run it
        # create play with tasks
        play_source =  dict(
                name = "Ansible Play",
                hosts = 'all',
                gather_facts = 'no',
                tasks = [
                    dict(action=dict(module='shell', args="sh /root/test.sh"), register='shell_out'),
                    #dict(action=dict(module='shell', args="echo 'testetet' > one.txt"), register='shell_out'),
                    dict(action=dict(module='debug', args=dict(msg='{{shell_out.stdout}}')))
                 ]
            )
        play = Play().load(play_source, variable_manager=self._variable_manager, loader=self._loader)
        
        tqm = None
        try:
            tqm = TaskQueueManager(
                      inventory = self._inventory,
                      variable_manager = self._variable_manager,
                      loader = self._loader,
                      options = self._options,
                      passwords = self._passwords,
                      stdout_callback = self._results_callback,  # Use our custom callback instead of the ``default`` callback plugin
                  )
            result = tqm.run(play)
            print result
            return result
        finally:
            if tqm is not None:
                tqm.cleanup()
            
        