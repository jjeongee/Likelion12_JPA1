package likelionjpa.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
	public class JpashopApplication {
		public static void main(String[] args) {
			Lombok lombok = new Lombok();
			lombok.setData("string");
			String data = lombok.getData();
			System.out.println("hello=" + data);
			SpringApplication.run(JpashopApplication.class, args);
		}

}