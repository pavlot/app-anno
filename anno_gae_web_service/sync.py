'''
Created on Jun 13, 2013

@author: sergey
'''
import webapp2
import logging
import json
from FeedbackComment import FeedbackComment
from google.appengine.api import users

class AnnoSync(webapp2.RequestHandler):
    
    JSON_REQUEST_PARAM_NAME = "jsonData"
    JSON_UPDATED_OBJECT = "updatedObjects"
    
    def get(self):
        self.response.out.write(self.proceedRequest(self.request))
        
    def post(self):
        self.response.out.write(self.proceedRequest(self.request))
        
    def proceedRequest(self, request):
        
        logging.info("Recived json string" + request.get(AnnoSync.JSON_REQUEST_PARAM_NAME));
        recivedJson  = json.loads(request.get(AnnoSync.JSON_REQUEST_PARAM_NAME))
        
        updatedObjects = recivedJson[AnnoSync.JSON_UPDATED_OBJECT]
        for item in updatedObjects:
            comment = FeedbackComment(user = users.get_current_user(), userId = users.get_current_user().user_id())
            comment.createComment(item)
        return "{answer:hello}"