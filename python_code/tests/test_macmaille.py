from macmaille import Macmaille

class test:
    #Class to test Macmaille.py
    def __init__(self):
        print "McMaille test"
    
    def read_test(self):
        #test the read output function of macmiallle
        mac = Macmaille('/home/sfz19839/DAWN_stable/comparison/testdata/test1.hkl',1.541800, 0.0000, 0,'mac_test')
        num1,num2 = mac.read_output('/home/sfz19839/DAWN_stable/comparison/testdata/mcm_output.imp')
        # are the shapes right?
        assert num1.shape == (5,6)
        assert num2.shape == (5,6)
    
    def write_test(self):
        #Tests the full write functions of write input
        mac = Macmaille('/home/sfz19839/DAWN_stable/comparison/testdata/test1.hkl',1.541800, 0.0000, 0,'mac_test')
        mac.writeinput()
        with open('/home/sfz19839/DAWN_stable/comparison/testdata/'+mac.title+'.dat','r') as f:
            with open('/home/sfz19839/DAWN_stable/comparison/testdata/mac_input_test.txt','r') as g:
                flines  = f.readlines()
                glines = g.readlines()
                for l1,l2 in zip(flines,glines):
                    assert l1 == l2
                g.close()
            f.close()
    
    def write_test2(self):
        # tests the write function with parameters added
        mac = Macmaille('/home/sfz19839/DAWN_stable/comparison/testdata/test1.hkl',1.541800, 0.0000, 0,'mac_test2')
        key_chain = {'Symmetry codes':None,'W': None,'Nind':None,'Pmin':None, 'Pmax':None, 'Vmin':None, 'Vmax':None, 'Rmin':None, 'Rmax':None, 'Rmaxref':None,'Spar':None, 'Sang':None,'Ntests':None, 'Nruns':None}
        for k in key_chain.iterkeys():
            mac.add_keywords(k, 1)
        mac.writeinput()
        with open('/home/sfz19839/DAWN_stable/comparison/testdata/mac_input_test2.txt','r') as f:
            with open('/home/sfz19839/DAWN_stable/comparison/testdata/mac_test2.dat', 'r') as g:
                flines = f.readlines()
                glines = g.readlines()
                for l1,l2 in zip(flines,glines):
                    assert l1 == l2
        
    def call_test(self):
        #tests the call function at the moment this should return 1
        mac = Macmaille('/home/sfz19839/DAWN_stable/comparison/testdata/test1.hkl',1.541800, 0.0000, 0,'mac_test')
        assert mac.call() == 1
        
        


if __name__ == '__main__':
    test().write_test2()