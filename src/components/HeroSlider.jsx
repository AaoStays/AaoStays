import { useEffect, useState } from "react";
import SearchBar from "./SearchBar";

const images = ["/hero1.jpg", "/hero2.jpeg", "/hero3.jpeg", "/hero4.jpeg"];

export default function HeroSlider() {
  const [index, setIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setIndex((i) => (i + 1) % images.length);
    }, 3500);
    return () => clearInterval(interval);
  }, []);

  return (
<section
  className="hero"
  style={{ backgroundImage: `url(${images[index]})` }}
>
  <div className="hero-text">
    <p>A CHAIN OF HOMESTAYS</p>
    <h1>Welcome to AaoStays</h1>
    <h3>Your Comfort, Our Priority</h3>
  </div>

  <div className="hero-dots">
    {images.map((_, i) => (
      <span key={i} className={i === index ? "dot active" : "dot"}></span>
    ))}
  </div>

</section>

  );
}