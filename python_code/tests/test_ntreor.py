from ntreor import Ntreor
from Loader import Loader

class test:
    
    def read_test(self):
        data = Loader().load_data_ntreor('/home/sfz19839/DAWN_stable/comparison/testdata/test1.hkl')
        nt = Ntreor(data)
        output = nt.read_output('/home/sfz19839/DAWN_stable/comparison/testdata/treor90_output.imp')
        assert output != None
    
    def write_test(self):
        data = Loader().load_data_ntreor('/home/sfz19839/DAWN_stable/comparison/testdata/test1.hkl')
        nt = Ntreor(data[:,4])
        nt.title = 'ntreor_test'
        nt.add_keywords('CHOICE', 2)
        nt.write_input()
        with open('/home/sfz19839/DAWN_stable/comparison/testdata/ntreor_test.dat','r') as f:
            with open('/home/sfz19839/DAWN_stable/comparison/testdata/ntreor_input_test.txt','r') as g:
                glines = g.readlines()
                flines = f.readlines()
                for l1,l2 in zip(glines,flines):
                    assert l1 == l2
                g.close()
            f.close()

    def call_test(self):
        nt = Ntreor('/home/sfz19839/DAWN_stable/comparison/testdata/test1.hkl')
        nt.call()
    
    def help_test(self):
        nt = Ntreor('/home/sfz19839/DAWN_stable/comparison/testdata/test1.hkl')
        helper = nt._keylist_()
        assert helper == None
        
if __name__ == '__main__':
    test()         