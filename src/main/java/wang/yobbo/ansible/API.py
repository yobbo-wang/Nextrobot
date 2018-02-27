#!/usr/bin/env python
# -*- coding: UTF-8 -*-  
'''
Created on 2018年2月1日

@author: xiaoJ
'''

import APIUtil
import threading
from flask import Flask, jsonify, abort, make_response, request, url_for

app = Flask(__name__)

#获取待办任务
@app.route('/api/v1.0/tasks', methods=['GET'])
def get_tasks():
    #实例化
    apiUtil = APIUtil.APIUtil() 
    tasks = apiUtil.getTasks()
    return jsonify(tasks)

@app.route('/api/v1.0/tasks/<int:task_id>', methods=['GET'])
def get_task(task_id):
    apiUtil = APIUtil.APIUtil()
    task = apiUtil.get_task()
    return jsonify(task)

@app.route('/api/v1.0/exeTask', methods=['POST'])
def exeTask():
    #开启线程执行脚本
    apiUtil = APIUtil.APIUtil()
    thread = threading.Thread(target=apiUtil.exeTask ,args=()) # 开启子线程
    thread.start()
    return jsonify({'success': 'true'})

@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=8888)