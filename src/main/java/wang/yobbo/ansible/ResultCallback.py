# -*- coding: UTF-8 -*-
'''
Created on 2018年2月1日

@author: xiaoJ
'''

import json
import requests
from ansible.plugins.callback import CallbackBase

# 编写自定义回调插件
class ResultCallback(CallbackBase):
    """A sample callback plugin used for performing an action as results come in
    If you want to collect all results into a single object for processing at
    the end of the execution, look into utilizing the ``json`` callback plugin
    or writing your own custom callback plugin
    """
    
    #脚本执行完回调后，处理业务逻辑
    def v2_runner_on_ok(self, result, **kwargs):
        """Print a json representation of the result

        This method could store the result in an instance attribute for retrieval later
        """
        host = result._host
        print('v2_runner_on_ok:' + json.dumps({host.name: result._result}, indent=4))
        r = requests.post("http://172.31.14.86:8888/MS/api/result", json={host.name: result._result})
        print r.text
     
    def v2_runner_on_failed(self, result, ignore_errors=False):
        host = result._host
        print('v2_runner_on_failed:' + json.dumps({host.name: result._result}, indent=4))
        
    def v2_runner_on_skipped(self, result, **kwargs):
        host = result._host
        print('v2_runner_on_skipped:' + json.dumps({host.name: result._result}, indent=4))
        
    def v2_runner_on_unreachable(self, result, **kwargs):
        host = result._host
        print('v2_runner_on_unreachable:' + json.dumps({host.name: result._result}, indent=4))
    
    def v2_runner_on_no_hosts(self, task, **kwargs):
        host = task._host
        print('v2_runner_on_no_hosts:' + json.dumps({host.name: task._result}, indent=4))    