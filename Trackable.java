// Trackable.java
// এই ইন্টারফেসটা Doctor আর Hospital — দুইটা সম্পূর্ণ unrelated ক্লাস implement করবে।
// একটাই method: getStatus() — যে যার মতো নিজের status রিটার্ন করবে (polymorphism)।

public interface Trackable {
    String getStatus();
}
