# -*- coding: utf-8 -*-
"""
Created on Sat Oct 16 00:17:34 2021

@author: lucia
"""

import socket            
 
# next create a socket object
s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)        
print ("Socket successfully created")
# reserve a port on your computer in our
# case it is 6789 but it can be anything
port = 6789
# Add a variable to contain the number of the current client
# connected to the server              
Client_counter = 0 
s.bind(('', port))        
print ("socket bound to %s" %(port))
# put the socket into listening mode (Note: number 5 here represent the number of #connection the server can put on hold before refusing any more connections)
s.listen(5)    
print ("socket is listening")
# a forever loop until we interrupt it or
# an error occurs
while True :
# Establish connection with client.
  c, addr = s.accept()
  Client_counter+=1    
  print ('Connected to client number', Client_counter )
  print(addr)
 # Receive message from the client and decode it
  while True :
    sentence= c.recv(1024).decode()
    print (sentence)
# capitalize the received message
    Capital_Sentence = sentence.upper()
# Send the modified message back to the client encoded using UTF-8
    c.send(Capital_Sentence.encode())
    if Capital_Sentence == "CLOSE SOCKET" :
        break

