class Hello:
    __gui = None
    
    def __init__(self, gui):
        self.__gui = gui
        self.y = 0
 
    def run(self):
        return 'My Number'
    
    def foo(self,x):
        self.y += x
        
    def gety(self):
        return self.y