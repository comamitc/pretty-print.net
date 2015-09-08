from __future__ import with_statement
from fabric.api import *
import time
import os

curr_dir = os.getcwd()
dirs = {'local_client':  curr_dir + '/pp-cljs-client',
        'local_server':  curr_dir + '/pp-jvm-server',
        'remote_base':   '/data/www/pretty-print.net/',
        'remote_client': '/data/www/pretty-print.net/pp-cljs-client',
        'remote_server': '/data/www/pretty-print.net/pp-jvm-server'}

def server_down():
  run("pkill -f 'java.*pp-jvm-server'")

def server(fn, ctx, dir):
  with ctx(dir):
    fn("lein clean")
    fn("lein ring uberjar")

def server_up():
  run("nohup java -jar target/pp-jvm-server-0.1.0-standalone.jar &")  

def client(fn, ctx, dir):
  with ctx(dir):
    fn("sudo npm install")
    fn("lein clean")
    fn("lein cljsbuild once main-min")
    fn("grunt build")
    
def pull(dir):
  with cd(dir):
    run("git pull origin master")

@task
def test():
  #client(local, lcd, dirs['local_client'])
  server(local, lcd, dirs['local_server'])

@task
def deploy():
  env.hosts = ['root@192.241.224.183']
  pull(dirs['remote_base'])
  client(run, cd, dirs['remote_client'])
  server_down()
  server(run, cd, dirs['remote_server'])
  server_up()

