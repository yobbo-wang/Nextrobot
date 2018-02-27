# -*- coding: UTF-8 -*-  
'''
Created on 2018年2月1日

@author: xiaoJ
'''
import APIUtil


if __name__ == '__main__':
    apiUtil = APIUtil.APIUtil(123) #实例化
    apiUtil.getHosts()
    apiUtil.addHost([{'host':'127.0.00.1'}])    
    apiUtil.addScript([{'script': '123.3.3..'}])  
    print apiUtil.getTasks()