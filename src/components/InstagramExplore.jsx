const instaPosts = [
  { img: "/insta1.jpg", link: "https://www.instagram.com/" },
  { img: "/insta2.jpg", link: "https://www.instagram.com/" },
  { img: "/insta3.jpg", link: "https://www.instagram.com/" },
  { img: "/insta4.jpg", link: "https://www.instagram.com/" },
  { img: "/insta5.jpg", link: "https://www.instagram.com/" },
  { img: "/insta6.jpg", link: "https://www.instagram.com/" },
  { img: "/insta7.jpg", link: "https://www.instagram.com/" }
];

export default function InstagramExplore() {
  return (
    <section className="insta-explore">
      <h2>Explore Our Instagram</h2>
      <p>Real stays. Real guests. Real moments.</p>

      <div className="insta-track">
        <div className="insta-row">
          {instaPosts.concat(instaPosts).map((post, i) => (
            <a
              href={post.link}
              target="_blank"
              rel="noreferrer"
              key={i}
              className="insta-card"
            >
              <img src={post.img} />
            </a>
          ))}
        </div>
      </div>
    </section>
  );
}