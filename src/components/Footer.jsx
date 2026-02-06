import { FaFacebookF, FaWhatsapp, FaInstagram, FaEnvelope } from "react-icons/fa";
import { MdPrivacyTip, MdDescription, MdBusiness } from "react-icons/md";

export default function Footer() {
  return (
    <footer className="footer">

      {/* LEFT SIDE */}
      <div className="footer-left">
        <div className="footer-item">
          <MdPrivacyTip />
          <span>Privacy</span>
        </div>

        <div className="footer-item">
          <MdDescription />
          <span>Terms</span>
        </div>

        <div className="footer-item">
          <MdBusiness />
          <span>Company Details</span>
        </div>
      </div>

      {/* RIGHT SIDE */}
      <div className="footer-right">
        <h4>Contact Us</h4>

        <div className="footer-socials">
          <a href="#"><FaFacebookF /> Facebook</a>
          <a href="#"><FaWhatsapp /> WhatsApp</a>
          <a href="#"><FaInstagram /> Instagram</a>
          <a href="#"><FaEnvelope /> Mail</a>
        </div>
      </div>

    </footer>
  );
}
