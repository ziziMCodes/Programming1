package university;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.random.RandomGenerator;

/** Manages the human resources of the university, including staff and their salaries. */
public class HumanResource {
  HashMap<Staff, Float> staffSalary = new HashMap<>(); // salary of staff

  /** Creates a new instance of HumanResource. */
  public HumanResource() {}

  /**
   * Retrieves an iterator over the staff members.
   *
   * @return Iterator over the staff members.
   */
  public Iterator<Staff> getStaff() {
    return staffSalary.keySet().iterator();
  }

  /**
   * Gets the number of staff members in staffSalary.
   *
   * @return Number of staff members.
   */
  public int getStaffSize() {
    return staffSalary.size();
  }

  /**
   * Add staff to the University. The salary of the new staff is randomly chosen between 9.5% and
   * 10.5% of the staff’s skill.
   *
   * @param staff Staff member to add
   */
  public void addStaff(Staff staff) {
    float salary = RandomGenerator.getDefault().nextFloat(0.095F, 0.105F) * (staff.getSkill());
    staffSalary.put(staff, salary);
    System.out.println(staff.getName() + "has been hired. Salary: " + salary);
  }

  /**
   * Gets the total salary of all staff members.
   *
   * @return Total salary of all staff members.
   */
  public float getTotalSalary() {
    float totalSalary = 0;
    for (float salary : staffSalary.values()) {
      totalSalary += salary;
    }
    return totalSalary;
  }

  /**
   * Replenishes the stamina of all staff members.
   *
   * @param staffSalary Map of staff members and their salaries.
   */
  public void replenishStamina(HashMap<Staff, Float> staffSalary) {
    for (Map.Entry<Staff, Float> entry : staffSalary.entrySet()) {
      Staff staff = entry.getKey();
      staff.replenishStamina();
    }
  }
}
