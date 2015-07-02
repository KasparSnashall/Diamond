import numpy as np
import os

class Loader:
    # this class contains all loading functions
    # mainly used in unit tests
    
    def __init__(self):
        print "Loader"
        
    
    def load_data(self,data1,data2,colnum = 3):
        # load in all data
        print "Loading data"
        
        data = data1,data2
        print data
        new_data = []
        for d in data:
            print d
            # find the data files or continue
            if type(d) == str:
                if os.path.exists(d):
                    # check if path is there
                    # find file type if not known then raise type error
                    if '.hkl' in d:
                        new_data.append(self.load_hkl_data(d))
                else:
                    print "data file type not known, try making it into list?"
                    raise TypeError
        print "data loaded successfully"
        print new_data
        return new_data[0],new_data[1]
        
        
    def load_hkl_data(self,data):
        # loads a hkl file colnum  is 3,4 for d_space,intensity respectively 
        d1 =  np.loadtxt(data,skiprows = 1)
        d1 = d1[:,3] # returns a column of data or returns an array
        print d1
        return d1
        
    def load_data_mac(self,data):
        d =  np.loadtxt(data,skiprows = 1)
        return d
    
    def load_data_ntreor(self,data):
        #will add more to this function?
        d = np.loadtxt(data, skiprows = 1)
        return d
        
if __name__ == "__main__":
    Loader()