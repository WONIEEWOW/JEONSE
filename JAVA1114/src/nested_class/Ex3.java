package nested_class;

public class Ex3 {

	public static void main(String[] args) {
		/*
		 * 익명(anonymous) 객체
		 * - 이름이 없는 객체
		 * - 클래스 선언과 객체의 생성을 동시에 하기 때문에 단 한번만 
		 *   사용될 수 있고 오직 하나의 객체만을 생성할 수 있는 일회용 클래스
		 * - 이름이 없기 때문에 생성자도 가질 수 없다.
		 * - 부모클래스의 이름이나 구현하고자 하는 인터페이스 이름을 
		 *   사용하여 정의하므로 하나의 클래스로 상속받는 동시에 인터페이스를
		 *   구현하거나 하나 이상의 인터페이스를 구현할 수 없다.
		 * - 하나의 클래스를 상속받거나 단 하나의 인터페이스만을 구현할 수 있다.
		 * 
		 * < 익명 객체 문법 >
		 * new 슈퍼클래스이름() {
		 * 		// 멤버 선언
		 * };
		 * 
		 * 또는
		 * 
		 * new 구현인터페이스이름() {
		 * 		// 멤버 선언
		 * };
		 */
		
		Parent p = new Child(); // 업캐스팅 발생!
		p.wake();  // 6시에 일어납니다! 출근합니다!
		
		Child c = new Child();
		c.method2(new Child2()); // 4시에 일어납니다! 공부합니다!
		//=> method2()의 파라미터가 Parent p이므로 서브클래스인
		//	 Child2 클래스의 객체가 파라미터가 값으로 사용될 수 있다! 
		
		System.out.println("-------------------------------------");
		
		AnonymousClass anony = new AnonymousClass();
		//=> 익명 클래스를 활용하기 위해 인스턴스 생성
		
		anony.p.wake(); // 6시에 일어납니다! 출근합니다!
		//=> 익명클래스 생성 및 메서드 호출
		
		System.out.println();
		
		Parent parent = anony.p; //p는 Parent 타입이므로 Parent 타입 변수에 저장 가능
		parent.wake(); // 6시에 일어납니다! 출근합니다!
		
		System.out.println("-------------------------------------");
		
//		C c = new C();		
		//=> 지역(로컬) 내부 클래스를 객체를 바로 생성할 수 없음!
		//=> 즉, C 클래스를 사용하려면 클래스를 바로 생성하는 것이 아니라 
		//	 method1() 메서드 호출을 통해 생성된 객체를 리턴(반환) 받아야 함
		Parent parent2 = anony.method1();
		parent2.wake(); //5시에 일어납니다! 강아지와 함께 산책합니다!
		
		System.out.println();
		
		Parent parent3 = anony.anonyMethod1();
		parent3.wake(); //5시에 일어납니다, 고양이와 함께 산책합니다!
		
		System.out.println("----------------------------");
		
		anony.method2(new Parent() {
			public void study() {
				System.out.println("공부합니다!");
			}

			@Override
			public void wake() {
				System.out.println("4시에 일어납니다!");
				study();
			}
		}
		);
		
		// ===================================================
		
//		I i = new I();
		//=> 인터페이스는 생성자가 요소에 없기때문에 인스턴스를 만들 수 없다 
		
		//단, 인터페이스 익명 개체 사용
		I i = new I() {

			@Override //alt+shift+s -> v
			public void wake() {
				System.out.println("9시에 일어납니다!");
			}
		};
		i.wake();
		
		ImplementClass ic = new ImplementClass();
		ic.wake(); //9시에 일어납니다
		
	} //main()

} //class Ex3


class ImplementClass implements I {

	@Override
	public void wake() {
		System.out.println("9시에 일어납니다");
	}
	
} //class implementClass

//================================================
//인터페이스 익명 개체 
interface I {
	public abstract void wake();
}

class Parent {
	public void wake() {
		System.out.println("7시에 일어납니다!");
	}
}

class Child extends Parent {
	public void work() {
		System.out.println("출근합니다!");
	}

	@Override
	public void wake() {
		System.out.println("6시에 일어납니다!");
		work();
	}	
	
	public void method2(Parent p) {
		p.wake();
	}
	
} //class Child


class Child2 extends Parent {
	public void study() {
		System.out.println("공부합니다!");
	}

	@Override
	public void wake() {
		System.out.println("4시에 일어납니다!");
		study();
	}
	
} //class Child2


// 익명 객체 활용
class AnonymousClass {
	// AnonymousClass 클래스의 필드(멤버변수) 선언
	Parent p = new Parent() {
		public void work() {
			System.out.println("출근합니다!");
		}

		@Override
		public void wake() {
			System.out.println("6시에 일어납니다!");
			work();
		}			
	};
	
	//지역(로컬)내부 클래스 활용
	public Parent method1() { // 메서드의 리턴타입이 Parent
		class C extends Parent { //리턴타입이 Parent이므로 Parent 타입의 객체를 
			//반환하기 위해 메서드 내부에 클래스 정의 
			//Parent 클래스를 상속받는 C 클래스 
			
			
			public void walk() {
				System.out.println("강아지와 함께 산책합니다!");
			}

			@Override
			public void wake() {
				System.out.println("5시에 일어납니다!");
				walk();
			}
			
		}
		return new C(); // C클래스를 생성하고 리턴(반환)함
	}
	
	//=> 위의 지역 내부 클래스를 익명 내부 클래스로 변경하면 아래와 같음 
	public Parent anonyMethod1() { 
		return new Parent() {
			public void walk() {
				System.out.println("고양이와 함께 산책합니다!");
			}

			@Override
			public void wake() {
				System.out.println("5시에 일어납니다");
				walk();
			}
		
		};
		
	}
	
	public void method2(Parent p) {
		p.wake();
	}
	
	
} //class AnonymousClass

