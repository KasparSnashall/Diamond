import numpy as np
import os

class Loader:
    """
    Loader Class doc string
    ###########################
    Class designed to handle all loading of files or data sets
    
    Attributes
    -----------------
    None
    
    Methods
    -------------------
    
    __init__()
    -------------
    1 argument self
    pass
    
    load_data()
    -----------
    4 arguments self,data1[list or str], data2[list or str], colnum = 3 [int column to be used]
    calls other functions to make data list
    or if all data is list
    returns data1,data2
    
    load_hkl_data()
    ------------------
    2 arguments self, str filepath
    loads a hkl file using numpy
    loads third column
    returns list
    
    load_data_mac()
    ----------------
    2 arguments self, str filepath
    loads file
    skips row 1
    returns array
    
    load_data_ntreor()
    -------------------
    stub same as load_data_mac()
    to be replaced
    
    
    
    """
    
    def __init__(self):
        pass
        
    
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
                        print "data file type not known, try making it into another type?"
                        raise TypeError
                else:
                    print "No such file exists"
                    raise ValueError
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