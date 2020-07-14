package entity;

public class PrisonList {
	private int id;
	private String name;
	private String address;
	private int quantity;
	private int capacity;
	private String img;
	
	public PrisonList() {}
	
	public PrisonList(int id, String name, String address, int quantity, int capacity, String img) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.quantity = quantity;
		this.capacity = capacity;
		this.img = img;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "PrisonList [id=" + id + ", name=" + name + ", address=" + address + ", quantity=" + quantity
				+ ", capacity=" + capacity + ", img=" + img + "]";
	}


	

}
