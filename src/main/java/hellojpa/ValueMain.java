package hellojpa;

public class ValueMain {

    public static void main(String[] args) {

        int a = 10;
        int b = 10;

        System.out.println("a == b : " + (a == b));

        Address address1 = new Address("city", "street", "1000");
        Address address2 = new Address("city", "street", "1000");

        System.out.println("address1 == address2 : " + (address1 == address2));
        // 값 타입의 비교는 equals . 클래스에 오버라이드해서 사용.
        System.out.println("address1 equals address2 : " + (address1.equals(address2));

    }
}
