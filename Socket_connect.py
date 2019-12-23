import socket  
import time
import threading
import os
import serial 
import binascii

def initialize():
    try:
        # 端口，GNU / Linux上的/ dev / ttyUSB0 等 或 Windows上的 COM3 等
        portx = "com4"
        # 波特率，标准值之一：50,75,110,134,150,200,300,600,1200,1800,2400,4800,9600,19200,38400,57600,115200
        bps = 9600
        # 超时设置,None：永远等待操作，0为立即返回请求结果，其他值为等待超时时间(单位为秒）
        timex = 0
        # 打开串口，并得到串口对象
        ser = serial.Serial(portx, bps, timeout=timex)
        print("串口详情参数：", ser)
        # 写数据
        ser.close()  # 关闭串口
        print("初始化成功")

    except Exception as e:
        print("---异常---：", e)


def execute(order):
    try:
        Action = ""
        # 端口，GNU / Linux上的/ dev / ttyUSB0 等 或 Windows上的 COM3 等
        portx = "com4"
        # 波特率，标准值之一：50,75,110,134,150,200,300,600,1200,1800,2400,4800,9600,19200,38400,57600,115200
        bps = 9600
        # 超时设置,None：永远等待操作，0为立即返回请求结果，其他值为等待超时时间(单位为秒）
        timex = 0
        # 打开串口，并得到串口对象
        ser = serial.Serial(portx, bps, timeout=timex)
        print("串口详情参数：", ser)

        # 生成命令
        stx = 0x02
        etx = 0x03
        exam_code = 0x02
        for i in order:
            d = ord(i) #单个字符转换成ASCii码
            exam_code = exam_code^d
            Action = Action + hex(d) + " "  #将单个字符转换成的ASCii码相连
        exam_code = exam_code^0x03

        Action = "0x02 " + Action + "0x03 " + hex(exam_code) + " "
        


        
        #result = ser.write('\x02\x31\x31\x5A\x52\x03\x09'.encode("gb2312"))  # 写数据
        result = ser.write(Action.encode("utf-8"))
        print("写总字节数:", result)

        ser.close()  # 关闭串口

    except Exception as e:
        print("---异常---：", e)


def getipaddrs(hostname):#只是为了显示IP，仅仅测试一下  
	result = socket.getaddrinfo(hostname, None, 0, socket.SOCK_STREAM)  
	return [x[4][0] for x in result]  
   

initialize()
host = ''  #为空代表为本地host  
hostname = socket.gethostname()  
hostip = getipaddrs(hostname)  
print('host ip', hostip)#应该显示为：127.0.1.1  
port = 9999     # Arbitrary non-privileged port  
sk = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  
sk.bind((host, port))  
sk.listen(4)  
Flag = True
conn, addr = sk.accept()  
print('Connected by', addr)  
conn.sendall(b'*sucessful connection\n')



def sendData():
	txt_count = 1000000
	global Flag
	while Flag:  
		time.sleep(1)
		filename = "F:\Textbooks4\jiayou\\"+str(txt_count)+".txt"
		if os.path.exists(filename):
			with open(filename) as f:
				line_count = 0
				for line in f:
					if line_count < 17:
							s = '*'+line
							conn.sendall(s.encode())
							line_count+=1

					
					if line_count >= 17:
						conn.sendall(line.encode())
						line_count+=1
						

			print(line_count)

		txt_count+=1;


def recvData():
    global Flag
    while Flag:
        f = "0x02"
        data = conn.recv(1024).decode()  
        if data == '0\n': 
            Flag = False
        else:  
            s = data.split("\n")
            print(type(s))
            print(s)
            
            for i in range(len(s)-2):
                execute(s[i])
                time.sleep(1)
            
    return Flag  




def main():

	t1 = threading.Thread(target=recvData)
	t2 = threading.Thread(target=sendData)
	t1.start()
	t2.start()
	t1.join()
	t2.join()



main()
conn.close()   
print("断开连接")




