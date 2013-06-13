'''
Created on Jun 13, 2013

@author: sergey
'''
from AnnoSyncEntity import AnnoSyncEntity
from google.appengine.ext import db


class FeedbackComment(AnnoSyncEntity):
    '''
    classdocs
    '''
    client_id = db.StringProperty()
    screenshot_key = db.StringProperty()
    comment = db.StringProperty()
    x = db.StringProperty()
    y = db.StringProperty()
    direction = db.StringProperty()

    def createComment(self, data):
        for name, value in data.items():
            setattr(self, name, value)
        self.put() 