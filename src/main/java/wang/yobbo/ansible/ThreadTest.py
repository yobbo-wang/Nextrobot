# -*- coding: UTF-8 -*-  
'''
Created on 2018年2月1日

@author: xiaoJ
'''
#!/usr/bin/python
# -*- coding: UTF-8 -*-
 
import threading
import time
 
exitFlag = 0

class ThreadTest (threading.Thread):   #继承父类threading.Thread
    def __init__(self, threadID, name, counter):
        threading.Thread.__init__(self)
        self._threadID = threadID
        self._name = name
        self._counter = counter
    def run(self):
        print "Starting " + self._name
        print_time(self._name, self._counter, 5)
        print "Exiting " + self._name

        
def print_time(threadName, delay, counter):
    while counter:
        time.sleep(delay)
        print "%s: %s" % (threadName, time.ctime(time.time()))
        counter -= 1

        
# 创建新线程
thread1 = ThreadTest(1, "Thread-1", 1)
thread2 = ThreadTest(2, "Thread-2", 2)
  
# 开启线程
thread1.start()
thread2.start()
  
print "Exiting Main Thread"        
        
        
# def rundd():
#     t = threading.Thread(target=print_time,args=('thread-1',1, 5))
#     t2 = threading.Thread(target=print_time,args=('thread-2',2, 5))
#     t.start()
#     t2.start()
#     
# if __name__ == '__main__':   
#     rundd()             
        
            