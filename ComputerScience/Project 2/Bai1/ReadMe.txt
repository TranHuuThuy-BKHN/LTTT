Input: bao gồm:

	-File dfa.jff ở định dạng JFLAP biểu diễn Otomat hữu hạn M, và

	một xâu nhị phân w.

Output: thông báo ra màn hình:

	-Yes nếu xâu w ∈ L(M).

	-No nếu xâu w ∉ L(M).

+Chương trình viết bằng ngôn nhữ Java 8, sử dụng module đọc file xml - jdom2.2.0.6

+Mô tả lớp được đính kèm hình ảnh 'Class Diagram.png'

+Mã nguồn thư mục trong thư mục Project 2\Bai1\DFA_JFLAP\src

+Ngoài ra để chạy chương trình trên script mà không cần vào một IDEA nào, vào thư mục Project 2\Bai1\DFA_JFLAP\out\artifacts\DFA_JFLAP_jar. Chạy với lệnh java -jar DFA_JFLAP.jar pathfilejff
	VD : java -jar DFA_JFLAP.jar dfa.jff