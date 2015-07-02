from comparator import Comparator
from Loader import Loader

class test:
    #Class to test comparator.py
    def __init__(self):
        # runs all unit tests 
        self.load_data_test()
        # loads test data
        self.data1,self.data3 = Loader().load_data('/home/sfz19839/DAWN_stable/comparison/testdata/test1.hkl','/home/sfz19839/DAWN_stable/comparison/testdata/test3.hkl')
        self.binary_test1()
        self.binary_test2()
        self.optimise_test()
        self.fraction_test1()
        self.fraction_test2()
        self.weight_test()
        self.both_test()

    def load_data_test(self):
        #test load test data, try loading data
        load = Loader()
        set1,set2 = load.load_data('/home/sfz19839/DAWN_stable/comparison/testdata/test1.hkl','/home/sfz19839/DAWN_stable/comparison/testdata/test3.hkl')
        assert set1 != []
        assert set2 != []

    def binary_test1(self):
        #test that compares two identical sets
        boby = Comparator(self.data1,self.data1)
        results = boby.compare(option = "b")
        print results
        assert results == 100

    def binary_test2(self):
        #test compares two non identical sets
        bob = Comparator(self.data1,self.data3)
        results = bob.compare(option = "b")
        print results
        assert results != 100

    def fraction_test1(self):
        bob = Comparator(self.data1,self.data1)
        q = bob.compare(option = "f")
        assert q == 100

    def fraction_test2(self):
        # compares the intensities
        bob = Comparator(self.data1,self.data3)
        q = bob.compare(option = "f")
        assert q == 1

    def both_test(self):
        # tests both functions
        bob = Comparator(self.data1,self.data3)
        assert bob.compare() != None
    
    def weight_test(self):
        bob = Comparator(self.data1,self.data3)
        bob.weights.append([0,10,3])
        bob.weights.append([20,25,5])
        print bob.weights
        assert bob.weights == [[0,10,3],[20,25,5]]
        assert bob.compare() != None
        assert bob.compare(option = "f") != None
        assert bob.compare(option = "b") != None
    
    def optimise_test(self):
        bob = Comparator(self.data1,self.data3)
        bob.tolerance = 5
        bob.steprange = -0.6,0.6,400
        bob.optimise = True
        opt_value = bob.compare('b')
        bob.optimise = False
        bad_value = bob.compare('b')
        assert opt_value == 77
        assert bad_value == 1

if __name__ == '__main__':
    test().fraction_test2()