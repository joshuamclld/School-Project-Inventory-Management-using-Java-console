
<p align="center">
   <img src="https://img.shields.io/badge/Java-Console%20App-blue?style=for-the-badge&logo=java" alt="Java Console App"/>
   <img src="https://img.shields.io/badge/OOP-Design-green?style=for-the-badge" alt="OOP Design"/>
   <img src="https://img.shields.io/badge/CSV%20Export-Available-orange?style=for-the-badge" alt="CSV Export"/>
</p>

<h1 align="center">Case Study Inventory Management</h1>

<p align="center">
   <b>A modern, object-oriented Java console app for managing eatery inventory.</b><br>
   <i>Add, edit, remove, and track items with descriptions, quantities, and CSV export support.</i>
</p>

<hr/>

## 🚀 Features

<ul>
   <li><b>OOP Design:</b> Clean separation of logic using <code>InventoryManager</code> and <code>Item</code> classes</li>
   <li><b>User-Friendly Menu:</b> Intuitive console interface</li>
   <li><b>Add/Edit/Remove Items:</b> Manage inventory with item name, quantity, and description</li>
   <li><b>Low Stock Alerts:</b> View items with low quantity</li>
   <li><b>CSV Export:</b> Export inventory for external use</li>
   <li><b>Persistent Storage:</b> Inventory saved to file</li>
   <li><b>Input Validation:</b> Robust error handling for user input</li>
</ul>

<hr/>

## 📦 Project Structure

```text
InventoryManagement/
├── CabandingsEateryInventoryManagement.java  # Main entry point
├── InventoryManager.java                     # Inventory logic (OOP)
├── Item.java                                # Item data class
├── inventory.txt                            # Inventory data (auto-generated)
├── inventory_export.csv                     # CSV export (on demand)
└── ...
```

<hr/>

## 🛠️ How to Run

<ol>
   <li><b>Compile:</b>
      <pre><code>javac *.java</code></pre>
   </li>
   <li><b>Run:</b>
      <pre><code>java CabandingsEateryInventoryManagement</code></pre>
   </li>
</ol>

<blockquote>
<b>Tip:</b> If your files are in a folder, use the <code>-cp</code> flag:
<pre><code>javac InventoryManagement/*.java
java -cp InventoryManagement CabandingsEateryInventoryManagement</code></pre>
</blockquote>

<hr/>

## 📝 Example Usage

```text
§========================§
|                        |
|  •Cabanding's Eatery•  |
|  Inventory Management  |
|  1. View Inventory     |
|  2. Add Item           |
|  3. Edit Item          |
|  4. Remove Item        |
|  5. Export to CSV      |
|  6. Save & Exit        |
|                        |
§========================§
Enter Code from 1 to 6 :
```

<hr/>

## 📋 Sample Inventory Data

```text
coffee : 50 : packs
sugar : 20 : kilos
bread : 10 : loaves
```

<hr/>

## 🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

<hr/>

## 👨‍💻 Author

- Joshua Macalalad 

<hr/>

<p align="center">
   <i>Modern Java console inventory management for small businesses and eateries.</i>
</p>
