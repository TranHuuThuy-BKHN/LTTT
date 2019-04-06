# Python implementation of 
# the above approach 

n = int(input('nhập n = '))
S = ['0', '1'] 
k = len(S) 
S.sort() 
count = 0
# To store the indices 
# of the characters 
w = [-1] 
  
# Loop till w is not empty 
while w: 
  
    # Incrementing the last character 
    w[-1] += 1
    m = len(w) 
    if m == n: 
		print(''.join(S[i] for i in w))
		count += 1 
    
    # Repeating w to get a 
    # n-length string 
    while len(w) < n: 
        w.append(w[-m]) 
    
    # Removing the last character 
    # as long it is equal to 
    # the largest character in S 
    while w and w[-1] == k - 1: 
        w.pop() 

print(count)