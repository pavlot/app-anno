'''
Created on Jun 13, 2013

@author: sergey
'''
import webapp2
import logging
import json
from FeedbackComment import FeedbackComment
from google.appengine.api import users
from datetime import datetime
from utils.AnnoJsonEncoder import AnnoJsonEncoder

class AnnoSync(webapp2.RequestHandler):
    
    JSON_REQUEST_PARAM_NAME = "jsonData"
    JSON_UPDATED_OBJECT = "updatedObjects"
    JSON_TIMESTAMP = "lastUpdateDate"
    
    JSON_REQUEST_TYPE = "request_type"
    JSON_REQUEST_TYPE_KEYS = "generateKeys"
    JSON_REQUEST_TYPE_UPDATE =  "updateData"
    
    JSON_KEYS_COUNT = "keys_count";
    JSON_OBJECTS_KEYS = "objectsKeys";
    
    def get(self):
        self.response.out.write(self.proceedRequest(self.request))
        
    def post(self):
        self.response.out.write(self.proceedRequest(self.request))
        
    def proceedRequest(self, request):
        logging.info("Recived json string" + request.get(AnnoSync.JSON_REQUEST_PARAM_NAME));
        recivedJson  = json.loads(request.get(AnnoSync.JSON_REQUEST_PARAM_NAME))
        
        if recivedJson[AnnoSync.JSON_REQUEST_TYPE] == AnnoSync.JSON_REQUEST_TYPE_KEYS:
            self.generateKeys(recivedJson)
        else:
            self.updateData(recivedJson)
            
        recivedJson[AnnoSync.JSON_TIMESTAMP] = datetime.now().strftime("%Y-%m-%d %H:%M:%S:%f")
        answer = json.dumps(recivedJson, cls = AnnoJsonEncoder)
        logging.info("Server answer" + answer )
        return answer
    
    def generateKeys(self, data):
        comment = FeedbackComment(user = users.get_current_user(), userId = users.get_current_user().user_id())
        data[AnnoSync.JSON_OBJECTS_KEYS] = comment.generateKeys(data[AnnoSync.JSON_KEYS_COUNT])
        
    def updateData(self, data):
        updatedObjects = data[AnnoSync.JSON_UPDATED_OBJECT]
        for item in updatedObjects:
            comment = FeedbackComment(user = users.get_current_user(), userId = users.get_current_user().user_id())
            comment.createComment(item)
        
        
    
    
    
    
    
    
    
    
    
    
    