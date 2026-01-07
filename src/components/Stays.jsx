const stays = [
  { name: "Rosemerry (Hatigaon)", price: "₹1499/night", img: "/s1.jpeg", description: "Entire Family Home"},
  { name: "Hamsa (Hatigaon)", price: "₹1399/night", img: "/s2.jpeg", description: "Bright & Comfortable Home"},
  { name: "Aaostays (Beltola)", price: "₹1299/night", img: "/s3.jpg",  description: "Fully Equipped Kitchen Stay"},
  { name: "GreenNest Retreat (Guwahati)", price: "₹1399/night", img: "/s4.jpg", description: "Nature View • Garden Seating"},
];

export default function Stays() {
  return (
    <section className="stays">
      <h2>Family-Friendly Stays</h2>
      <h3>Comfortable homes for the whole family</h3>
      <div className="stay-grid">
        {stays.map((s, i) => (
          <div className="stay-card" key={i}>
            <img src={s.img} />
            <h4>{s.name}</h4>
            <p className="price">{s.price}</p>
            <h5>{s.description}</h5>

             {/* BOOK BUTTON */}
    <button className="book-btn">Book Now</button>
    
          </div>
        ))}
      </div>
    </section>
  );
}
