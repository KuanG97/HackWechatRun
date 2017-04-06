# !/usr/bin/env python3
# -*- coding:utf-8 -*-

__author__ = "Evilmass"
__version__ = "1.0"
__email__ = "evilmass1ve@gmail.com"

import requests
import datetime
import json
import time
from hashlib import md5

data ={}


def formatDate():  # 生成时间戳
    nowtime = datetime.datetime.now()
    date = time.strftime('%Y-%m-%d')
    strtemp_date = date + ' 00:00:00'
    ledongli_date = time.strptime(strtemp_date, '%Y-%m-%d %H:%M:%S')
    finaldate = int(time.mktime(ledongli_date))
    return finaldate


def generateKey():  # 生成校验值
    Str = 'ldl_pro@' + str(formatDate()) + '#' + str(steps) + '$' + str(data['distance']) + '^' + str(data['calories']) + '&' + str(data['duration'])
    key = md5(bytes(Str.encode('utf-8'))).hexdigest()
    return key


def upload():
    url = 'http://walk.ledongli.cn/rest/dailystats/upload/v3?uid=' + str(uid)
    data['calories'] = data['duration'] = data['distance'] = data['steps'] = steps
    data['date'] = formatDate()
    data['key'] = generateKey()
    playload = {
        'pc': 'your_pc_value',
        'stats': json.dumps([data]),
    }
    headers = {
        'User-Agent':'okhttp/3.5.0',
        'Content-Type':'application/x-www-form-urlencoded',
        'Accept-Encoding': 'gzip, deflate',
        'Host':'walk.ledongli.cn',
    }
    response = requests.request("POST", url, data=playload, headers=headers)
    return response.text


if __name__ == '__main__':
    steps = int(input(u'请输入步数：'))  # 上限为98800
    uid = int(input(u'请输入uid：'))
    if "{\"ret\":[],\"errorCode\":0}" in upload():
        print(u'总计刷了%s步' % str(steps))
    else:
        print(u'uid不正确或网络错误')