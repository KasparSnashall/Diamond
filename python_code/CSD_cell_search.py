from ccdc.search import ReducedCellSearch
from collections import namedtuple


class CSD_cell_search:
    
    def __init__(self,angles,lengths):
        self.angles = angles
        self.cell_lengths = lengths
        self.lattice_centring = 'primitive'
    
    def search(self):
        # searches the csd data base for a crystal with known parameters returns best result
        cellA = namedtuple('CellAngles', ['alpha', 'beta', 'gamma']) # create a named tuple for ccdc module
        angles = list(self.angles) # make a list of angles
        more_angles = cellA(angles[0],angles[1],angles[2]) # append angles to the tuple
        
        cellL = namedtuple('CellLengths',['a','b','c'])
        cell_lengths = self.cell_lengths
        more_lengths = cellL(cell_lengths[0],cell_lengths[1],cell_lengths[2])
        
        query = ReducedCellSearch.Query(lengths = more_lengths,angles = more_angles ,lattice_centring = 'primitive' )
        searcher = ReducedCellSearch(query)
        
        hits = searcher.search() # search for all hits
        h = hits[0] # top hit
        print h.identifier, h.crystal.cell_lengths, h.crystal.cell_angles, h.crystal.lattice_centring # print the best result 

if __name__ == '__main__':
    csd = CSD_cell_search([108.75, 71.07000000000001,96.16],[11.372,10.272,7.359]) # the exact AABHTZ crystal parameters
    csd.search()