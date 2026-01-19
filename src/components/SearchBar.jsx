import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../home/Home.css";


export default function SearchBar() {
  const navigate = useNavigate();

  const [location, setLocation] = useState("");
  const [checkin, setCheckin] = useState("");
  const [checkout, setCheckout] = useState("");
  const [guests, setGuests] = useState({ adults:1, children: 0, pets: 0});
  const [showGuestBox, setShowGuestBox] = useState(false);

  const totalGuests = guests.adults + guests.children + guests.pets;
  return (
     <div className="search-box">
      <div className="location-dropdown">
        <div
         className="location-input"
         onClick={() => setLocation("Guwahati, Assam")}
         >
          {location || "Location"}
         </div>

         <div className="location-options">
          <div
           className="location-option"
           onClick={() => setLocation("Guwahati, Assam")}
           >
            Guwahati, Assam
           </div>
         </div>
      </div>

      <input type="date" value={checkin} onChange={e => setCheckin(e.target.value)} />

      <input type="date" value={checkout} onChange={e => setCheckout(e.target.value)} />

      <div className="guest-input" onClick={() => setShowGuestBox(!showGuestBox)}>
       {totalGuests === 1 ? "Guests" : `${totalGuests} Guests`}
      </div>

      <button onClick={() => navigate("/search-results")}>Search</button>

      {showGuestBox && (
        <div className="guest-popup">
          {["adults", "children", "pets"].map(type => (
            <div className="guest-row" key={type}>
              <div className="guest-label">
          <span className="guest-type">{type}</span>
        </div>
              

              <div className="guest-controls">
                <button onClick={() => setGuests({ ...guests, [type]: Math.max(type==="adults"?1:0, guests[type]-1) })}>-</button>
                 <span className="guest-count">{guests[type]}</span>
              <button onClick={() => setGuests({ ...guests, [type]: guests[type]+1 })}>+</button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}   