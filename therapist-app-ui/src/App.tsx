import { MapContainer } from "react-leaflet/MapContainer";
import { Circle, Marker, Polyline, Popup, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import { useDemoData, useRun } from "./api/apiComponents";
const fillBlueOptions = { fillColor: "blue", strokeColor: "black" };
const fillRedOptions = { fillColor: "red" };
const greenOptions = { color: "green", fillColor: "green" };
const purpleOptions = { color: "purple" };
function App() {
  // const api = useDemoData({
  //   headers: { "Content-Type": "application/json" },
  //   queryParams: {},
  // });
  const api = useRun({
    headers: { "Content-Type": "application/json" },
    queryParams: {},
  });
  return (
    <>
      {api.isLoading && <div>Loading...</div>}

      <MapContainer
        center={[17.419227, 78.510442]}
        zoom={11}
        scrollWheelZoom={false}
      >
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {api.data?.patientList!.map((item) => (
          <Circle
            key={item.name}
            center={[item.location!.latitude!, item.location!.longitude!]}
            pathOptions={{ color: "green", fillColor: "green" }}
            radius={500}
          >
         <Popup><pre>{JSON.stringify({...item,availability:[]},null,2)}</pre></Popup>
          </Circle>
        ))}
        {api.data?.therapistList!.map((item) => (
          <Circle
            key={item.name}
            center={[item.location!.latitude!, item.location!.longitude!]}
            pathOptions={{ color: "purple", fillColor: "purple" }}
            radius={500}
          >
            <Popup><pre>{JSON.stringify({...item,availability:[]},null,2)}</pre></Popup>
          </Circle>
        ))}
        {api.data?.appointmentList?.map((item) => (
          <Polyline
            pathOptions={{ color: "red" }}
            positions={[
              [
                item.patient!.location!.latitude!,
                item.patient!.location!.longitude!,
              ],
              [
                item.therapist!.location!.latitude!,
                item.therapist!.location!.longitude!,
              ],
            ]}
          />
        ))}
      </MapContainer>
      <pre>
        {JSON.stringify(
          api.data?.patientList?.map((x) => x.location),
          null,
          2
        )}
      </pre>
      <pre>
        {JSON.stringify(
          api.data?.therapistList?.map((x) => x.location),
          null,
          2
        )}
      </pre>
    </>
  );
}

export default App;
