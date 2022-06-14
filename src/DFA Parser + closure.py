class DFA :
    
    def __init__(self, transitionTable, finalStates, allowedChars, initialState):
        self.transitionTable = transitionTable
        self.finalStates = finalStates
        self.allowedChars = allowedChars
        self.initialState = initialState
    
    def validate(self, word):
        currentState = self.initialState
        for char in word:
            if char not in self.allowedChars:
                return False
            currentState = self.transitionTable[currentState][self.allowedChars.index(char)]
            
        return currentState in self.finalStates
        
    
    
    @staticmethod
    def closure(fa):
        allowedChars = fa.allowedChars
        transitionTable = []
        finalStates = []
        zStates = []
        newState = []
        
        newState.append(fa.initialState)
        zStates.append(newState)
        if fa.initialState in fa.finalStates:
            finalStates.append(0)
        
        z = 0 
        while z < len(zStates):
            zState = zStates[z]
            transitions = []
            
            for ci in range ( len(allowedChars) ):
                newState = []
                containsFinal = False
                for x in zState:
                    xState = fa.transitionTable[x][ci]
                    
                    if xState not in newState:
                        newState.append(xState)
                        
                        if xState in fa.finalStates:
                            containsFinal = True
                            if fa.initialState not in newState:
                                newState.append(fa.initialState)
                
                newState.sort()
                
                if newState not in zStates:
                    zStates.append(newState)
                    if containsFinal:
                        finalStates.append(len(zStates)-1)
                
                transitions.append(zStates.index(newState))
            
            transitionTable.append(transitions)    
            z+=1
            
        newInitialState = len(transitionTable)
        finalStates.append(newInitialState)
        transitionTable.append(transitionTable[0])
        return DFA(transitionTable, finalStates, allowedChars,newInitialState)
    

#TEST     
transitionTable = [
    [1,0],
    [2,1],
    [3,2],
    [1,3]
]

finalStates = [3]
allowedChars = ['a','b']

fa = DFA(transitionTable,finalStates,allowedChars,0)
fa.validate('abaa')
faClosured = DFA.closure(fa)