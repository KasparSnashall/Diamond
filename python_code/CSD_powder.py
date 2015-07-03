from ccdc.io import EntryReader
from ccdc.descriptors import PowderPattern
import numpy as np

class CSD_powder:
    # loads items, lists and performs calculations related to powder diffraction patterns
    def __init__(self,name):
        ""
        self.entry = EntryReader('CSD')
        self.crystal_name = name
        
    def load_d_space(self):
        # creates a d_space list with intensities as a second option
        crystal = self.entry.crystal(self.crystal_name)
        pattern = PowderPattern.from_crystal(crystal)
        self.wavelength = PowderPattern.Wavelength.Wavelength_CuKa1
        peak_thetas = []
        #intents = pattern.intensity
        two_t = pattern.two_theta
        intents = pattern.intensity # all pattern intensity
        intensity = [] # final list of intensities
        for i in pattern.tick_marks:
            l = i.two_theta # two theta vals
            for j,I in zip(two_t,intents):
                if abs(l-j) < 0.01:
                    # compare lists and find peak 2theta values the above assumption may be changed
                    peak_thetas.append(j) # tick two theta val
                    intensity.append(I) # add the intensity of those peaks to a list
                    break
        d_space = [] # list of d_spaces
        peak_thetas = np.array(peak_thetas)/2 # theta vals instead of 2theta
        peak_radians = peak_thetas*np.pi/180 # to radians
        for peak in peak_radians:
            d = self.wavelength/(2*np.sin(peak)) # get the space values in Angstorms
            d_space.append(d) # append final values to a list
            
        return d_space,intensity
    
    def load_intensities(self):
        # loads the intensities of a given crsytal
        crystal = self.entry.crystal(self.crystal_name)
        pattern = PowderPattern.from_crystal(crystal)
        return pattern.intensity
        
    def load_two_theta(self):
        crystal = self.entry.crystal(self.crystal_name)
        pattern = PowderPattern.from_crystal(crystal)
        return pattern.two_theta
        
        
if __name__ == "__main__":
    bob = CSD_powder('AABHTZ')
    num1,num2 = bob.load_d_space()
    print len(num1)