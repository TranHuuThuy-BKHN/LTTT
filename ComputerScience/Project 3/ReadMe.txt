Project 3: Đơn định hóa Otomat
Bạn hãy viết chương trình sau.

Input:
File nfa.jff ở định dạng JFLAP biểu diễn otomat đa định M.

Output:
File dfa.jff ở định dạng JFLAP biểu diễn otomat đơn định tương đương với otomat M.

Yêu cầu
Ta biết rằng trong Otomat định, những trạng thái không tới được từ trạng thái bắt đầu sẽ không có vai trò trong việc đoán nhận ngôn ngữ, và do đó có thể bỏ đi. Hãy loại bỏ những trạng thái này trong otomat đơn định mà bạn vừa tìm được.

Gợi ý: Những trạng thái có thể đạt được từ trạng thái bắt đầu có thể tìm được dùng thuật toán tìm kiếm theo chiều sâu DFS hoặc tìm kiếm theo chiều rộng BFS.


+Kí hiệu rỗng trong bài được biểu diễn là "epsilon"

+Mã nguồn thư mục trong thư mục Project 2\Bai2\DFA2\src

+Ngoài ra để chạy chương trình trên script mà không cần vào một IDEA nào, vào thư mục Project 3\out\artifacts\Project_3_jar. Chạy với lệnh java -jar filename.jar filename2.jff
	VD : java -jar Project 3.jar nfa.jff
