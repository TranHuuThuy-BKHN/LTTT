Input: Hai file m1.jff và m2.jff ở định dạng JFLAP biểu diễn hai Otomat đơn định M1 và M2.

Output: Hai file giao.jff và hop.jff ở định dạng JFLAP biểu diễn Otomat:

	-Otomat M giao đoán nhận ngôn ngữ L(M1)∩L(M2), và

	-Otomat M hợp đoán nhận ngôn ngữ L(M1)∪L(M2).


+Chương trình viết bằng ngôn nhữ Java 8, sử dụng module đọc file xml - jdom2.2.0.6

+Mô tả lớp được đính kèm hình ảnh

+Mã nguồn thư mục trong thư mục Project 2\Bai2\DFA2\src

+Ngoài ra để chạy chương trình trên script mà không cần vào một IDEA nào, vào thư mục Project 2\Bai2\DFA2\out\artifacts\DFA2_jar. Chạy với lệnh java -jar DFA2.jar pathfilejff1 pathfilejff2
	VD : java -jar DFA_JFLAP.jar m1.jff m2.jff
