import { useEffect, useState } from "react";
import "../home/Home.css";

const data = [
  { q: "What is AaoStays?", a: "AaoStays is a chain of verified premium homestays across India." },
  { q: "How do I book a stay?", a: "Search your city, choose stay and complete booking." },
  { q: "Are all stays verified?", a: "Yes, each stay is safety checked." },
  { q: "Can I cancel my booking?", a: "Free cancellation is available on selected stays." },
];

export default function FAQ() {
  const [index, setIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setIndex((prev) => (prev + 1) % data.length);
    }, 6000);

    return () => clearInterval(timer);
  }, []);

  return (
    <section className="faq">
      <h2>Frequently Asked Questions</h2>

      <div className="faq-slider">
        <div
          className="faq-track"
          style={{ transform: `translateY(-${index * 140}px)` }}
        >
          {data.map((item, i) => (
            <div className="faq-slide" key={i}>
              <h4 className="faq-question">{item.q}</h4>

              {/* key forces re-animation on every slide */}
              <p className="faq-answer" key={index}>
                {item.a}
              </p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}