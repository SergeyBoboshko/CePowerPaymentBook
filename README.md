# 📦 Compose Entity Starter Template

A minimal starter template for projects using **Compose Entity**. This template provides an initial setup for automatic UI generation and database handling with minimal configuration.

For detailed documentation, please visit the [Compose Entity KSP Manual](https://wool-fontina-39f.notion.site/Compose-Entity-KSP-1bbac9e714318004866fd9fd627a25e1).

## 🚀 Features
- **Automatic UI Generation** – Define your entities and get fully functional forms instantly.
- **Built-in Database Management** – No need to manually create DAOs or ViewModels.
- **Simplified Setup** – Just clone, build, and start working on your project.
- **Highly Customizable** – Supports custom UI elements and advanced configurations.

## 🛠 Installation & Usage

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/SergeyBoboshko/ComposeEntitySample.git
cd ComposeEntitySample
```

### 2️⃣ Rename the Package
Update the package name in the project files:
- Open `AndroidManifest.xml` and change `package="com.example.template"` to your desired package.
- Refactor the package name in the `src/main/java/com/example/template/` directory.

```sh
mv src/main/java/com/example/template src/main/java/com/your/package
```

### 3️⃣ Open in Android Studio
- Open the project in **Android Studio**.
- Ensure you have the latest **Compose Entity** dependencies.

### 4️⃣ Define Your Entities
Create your entities, and **Compose Entity** will handle everything:
```kotlin
@EntityCE
data class Product(
    @PrimaryKeyCE val id: Int,
    val name: String,
    @FormFieldCE(label = "Price", placeHolder="Input Price", type = FieldTypeHelper.DECIMAL)
    val price: Double
)
```

### 5️⃣ Run the Project 🚀
Your UI and database are automatically generated, and you can start using the app immediately.

## 📝 Customization
You can extend the default setup by:
- Adding **custom UI elements** using `customComposable`.
- Modifying **form layouts** for better UX.
- Implementing **custom save logic** within ViewModels.

## 📜 License
This project is licensed under the **MIT License**.

---

For more details, check out the **ComposeEntity** documentation. 🎯

