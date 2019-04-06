import xml.dom.minidom

listStep = []
# sâu cần kiểm tra
t = "1010100101001011111010101010101101"

# Open XML document using minidom parser 
DOMTree = xml.dom.minidom.parse("E:\\ComputerScience\\Project 2\\Bai2\\DFA2\\out\\artifacts\\DFA2_jar\\hop.jff")
automaton = DOMTree.documentElement

# Get all the transition in the automaton
listTransition = automaton.getElementsByTagName("transition")

# Print detail of each transition.
for transition in listTransition:
   fromm = transition.getElementsByTagName('from')[0]
   listStep.append(fromm.childNodes[0].data)
   to = transition.getElementsByTagName('to')[0]
   listStep.append(to.childNodes[0].data)
   read = transition.getElementsByTagName('read')[0]
   listStep.append(read.childNodes[0].data)

# xử lý phần <from> x <to> y <read> a, b
for index in range(0, len(listStep)):
   if len(listStep[index]) > 1 :
      temp = listStep[index].split(",  ")
      listStep[index] = temp[0]
      listStep.append(listStep[index - 2])
      listStep.append(listStep[index - 1])
      listStep.append(temp[0])

start = "0"
status = start

# tìm final
listState = automaton.getElementsByTagName("state")
for index in range(len(listState)):
   if listState[index].getElementsByTagName('final'):
      final = str(index)

# xử lí xâu xem thuộc dfa ko
while len(t) != 0:
   for index in range(0, len(listStep), 3):
      if (status == listStep[index] and t[0] == listStep[index + 2]):
         status = listStep[index + 1]
         break
   t = t[1:]

print(status)
if status == final:
   print("YES")
else:
   print("NO")