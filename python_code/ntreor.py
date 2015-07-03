from Loader import Loader
import numpy as np

class Ntreor:

    title = "ntreor_test"
    #The list of all possible variables
    standard_dict = {'KH':4,'KK':4,'KL':4,'KS':6,'THH':4,'THL':4,'THK':4,'THS':4,'OH1':2,'OK1':2,'OL1':2,'OS1':3,
                     'OH2':2,'OK2':2,'OL2':2,'OS2':4,'OH3':2,'OK3':2,'OL3':2,'OS3':4,'MH1':2,'MK1':2,'ML1':2,
                     'MS1':2,'MH2':2,'MK2':2,'ML2':2,'MS2':3,'MH3':2,'MK3':2,'ML3':2,'MS3':2,'MH4':2,'MK4':2,'ML4':2,'MS4':2,
                     'MONOSET':0,'MONOGRAM':1,'MONO':0,'SHORT':1,'USE':19,'IQ':'USE-3','LIST':0,'SELECT':0,'MERIT':10,
                     'NIX':1,'IDIV':1,'WAVE':1.5405981,'VOL':2000,'CEM':25,'D1':0.002,'SSQTL':0.05,'D2':0.0004,
                     'CHOICE':0,'DENS':0,'EDENS':0,'MOLW':0,'TRIC':0}

    def __init__(self,data):
        print "Ntreor program"
        self.data = data
        self.keywords = {}
        
    def add_keywords(self,key,value):
        if key not in self.standard_dict.iterkeys():
            print'Error Ntreor entered keyword not in standard dict see get_keywords() for accepted list with usual values \n or _keylist_() for full descriptions'
            raise ValueError
        else:
            self.keywords[key] = value
    
    def get_keywords(self):
        print self.standard_dict
        return self.standard_dict
        
    def read_output(self,file_name):
        # reads the imp output file of ntreor
        with open(file_name,'r') as f:
            lines = f.readlines()
            linenum = 0
            list1 = []
            for i in lines:
                linelist = []
                if "TOTAL NUMBER OF LINES =" in i:
                    for l in range(3):
                        linelist.append(lines[linenum +l+1])
                    list1.append(linelist)
                linenum += 1
            f.close() # close the file
        data_list = []
        for data in list1:
            data_array = [] # create a blank array
            for data_string in data: # take the strings
                d1 = data_string.replace('=','') # this removes all '=' as they can be troublesome
                d1 = d1.split() # split them up into a list
                data_array.append(d1) # append the list to the array as a line
            data_list.append(np.array(data_array)) # append the array to a list
        
        transposed_list = []
        for a_matrix in data_list:
            transposed_list.append(a_matrix.T) # add the transposed arrays
        # form of each array now
        #[['A' 'B' 'C']
        #['10.479499' '18.973003' '6.879375'] 
        #['.001495' '.002641' '.000804'] errors
        #['A' 'A' 'A']
        #['ALFA' 'BETA' 'GAMMA']
        #['90.000000' '106.454803' '90.000000'] 
        #['.000000' '.009824' '.000000'] errors
        #['DEG' 'DEG' 'DEG']]
        
        value_list = [] # list of lists of values, to be A,B,C,a,b,g
        errors_list = [] # list of list of uncertainty of values list
        
        for data in transposed_list:
            values = list(data[1,:])+list(data[5,:]) # get the 1st and 5th rows make into list and add
            errors = list(data[2,:])+list(data[6,:]) # " 2nd and 6th
            values = map(float, values) # convert them to floats
            errors = map(float,errors)
            value_list.append(values) # returns a list of values
            errors_list.append(errors) # returns a list of error vals
        
        return value_list,errors_list

    def write_input(self):
        ""
        data1 = self.data[0:20] # select first 20 elements only, I must test this!
        with open('/home/sfz19839/DAWN_stable/comparison/testdata/'+self.title +'.dat','w') as f:
            f.write(self.title +'\n') # set title
            for i in data1:
                f.write(str(i)+'\n') # add in data
            f.write('\n') # new line
            for j,k in self.keywords.iteritems():
                f.write(j.upper()+'='+str(k)+'\n') # add keywords in uppercase
            f.write('END*'+'\n') # add end statement
            f.write('0.00') # required for some code if not needed ignored
            f.close

    def call(self):
        print "to be added once compile complete"
        return None
     
    def _keylist_(self):
        # prints the key list for ntreor this is extensive
        with open('/home/sfz19839/DAWN_stable/comparison/documentation/Ntreor_keywords.txt','r') as f:
            print f.read()
            f.close()

    