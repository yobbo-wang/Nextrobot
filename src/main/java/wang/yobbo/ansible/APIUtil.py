# -*- coding: UTF-8 -*-  
'''
Created on 2018年2月1日

@author: xiaoJ
'''
import Runner

class APIUtil:
    '''
    API接口工具父类
    '''

    def __init__(self):
        '''
        Constructor
        '''  
    
    def getHosts(self):
    #get hosts
        print('get hosts ...')    
        
    def addHost(self, hosts):
    #add hosts
        print hosts
     
    def addScript(self, script):
        print 123    
       
    def getTasks(self):
        return [{'task': 'test'}]
       
    def get_task(self):
        return {'task': 'test'}
    
    def exeTask(self):
        runner = Runner.Runner()
        result = runner.run()
        return result
    