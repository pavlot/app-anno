'''
Created on Jun 13, 2013

@author: sergey
'''
from AnnoSyncEntity import AnnoSyncEntity
from google.appengine.ext import db
from datetime import datetime

class FeedbackComment(AnnoSyncEntity):
    
    JSON_SCREENSHOT_KEY = "screenshot_key"
    JSON_COMMENT = "comment"
    JSON_COORD_X = "x"
    JSON_COORD_Y = "y"
    JSON_DIRECTION = "direction"
    JSON_OBJECT_KEY = "object_key"
    JSON_UPDATE_TIME = "lastUpdateTimestamp"
    
    '''
    classdocs
    '''
    screenshot_key = db.StringProperty()
    comment = db.StringProperty()
    x = db.StringProperty()
    y = db.StringProperty()
    direction = db.StringProperty()

    def createComment(self, data):
        KeyForUpdate = db.get(data[self.JSON_OBJECT_KEY])
        if None != KeyForUpdate:
            self.updateExistingComment(KeyForUpdate, data)
        else:
            self.addNewComment(data)
            
    def addNewComment(self, data):
        self._key = db.get(data[self.JSON_OBJECT_KEY])
        for name, value in data.items():
            setattr(self, name, value)
        self.put()
        
    def updateExistingComment(self, KeyForUpdate, data):
        if KeyForUpdate.lastUpdateTimestamp < datetime.strptime(data[self.JSON_UPDATE_TIME], "%Y-%m-%d %H:%M:%S"):
            KeyForUpdate.screenshot_key =  data[self.JSON_SCREENSHOT_KEY]
            KeyForUpdate.comment =  data[self.JSON_COMMENT]
            KeyForUpdate.x =  data[self.JSON_COORD_X]
            KeyForUpdate.y =  data[self.JSON_COORD_Y]
            KeyForUpdate.direction =  data[self.JSON_DIRECTION]
            KeyForUpdate.put()
    
    def generateKeys(self, count):
        result = []
        if(count > 0):
            baseKey = db.Key.from_path('FeedbackComment', 1)
            ids =  db.allocate_ids(baseKey, count)
            idsRange = (ids[0], ids[1] + 1)
        
            for item in range(0, count):
                result.append(str(db.Key.from_path('FeedbackComment', idsRange[item])))
        
        return result
