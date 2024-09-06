import { MapContainer } from "react-leaflet/MapContainer";
import { Circle, Polyline, Popup, TileLayer, Tooltip } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import { useRun } from "./api/apiComponents";
import dayjs from "dayjs";
import { Appointment } from "./api/apiSchemas";
import { Box, Chip, Spinner } from "@prismane/core";
 
export interface Coordinates {
  latitude: number;
  longitude: number;
}
type IMap = {
  [key: string]: any[];
};
/**
 * Calculates the distance (in kms) between point A and B using earth's radius as the spherical surface
 * @param pointA Coordinates from Point A
 * @param pointB Coordinates from Point B
 * Based on https://www.movable-type.co.uk/scripts/latlong.html
 */
function haversineDistance(pointA: Coordinates, pointB: Coordinates): number {
  const radius = 6371; // km

  //convert latitude and longitude to radians
  const deltaLatitude = ((pointB.latitude - pointA.latitude) * Math.PI) / 180;
  const deltaLongitude =
    ((pointB.longitude - pointA.longitude) * Math.PI) / 180;

  const halfChordLength =
    Math.cos((pointA.latitude * Math.PI) / 180) *
      Math.cos((pointB.latitude * Math.PI) / 180) *
      Math.sin(deltaLongitude / 2) *
      Math.sin(deltaLongitude / 2) +
    Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2);

  const angularDistance =
    2 * Math.atan2(Math.sqrt(halfChordLength), Math.sqrt(1 - halfChordLength));

  return radius * angularDistance;
}
function App() {
  // const api = useDemoData({
  //   headers: { "Content-Type": "application/json" },
  //   queryParams: {},
  // });
  const api = useRun({
    headers: { "Content-Type": "application/json" },
    queryParams: {},
  });
  const sc = api.data?.schedule;
  return (
    <Box>
      {api.isLoading && <Spinner size={"lg"} />}
      <div>{/* {api.data?.patientList!.map((item) => ())} */}</div>
    <MapContainer
        center={[17.419227, 78.510442]}
        zoom={13}
        scrollWheelZoom={false}
      >
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {sc?.patientList!.map((item) => (
          <Circle
            weight={1}
            key={item.name}
            center={[item.location!.latitude!, item.location!.longitude!]}
            pathOptions={{ color: "green", fillColor: "green" }}
            radius={500}
          >
            <Tooltip direction={"top"} permanent className="patientTip">
              {item.criticality} ★
            </Tooltip>
            <Popup>
              <pre>
                {JSON.stringify({ ...item, availability: [] }, null, 2)}
              </pre>
            </Popup>
          </Circle>
        ))}
        {sc?.therapistList!.map((item) => (
          <Circle
            key={item.name}
            center={[item.location!.latitude!, item.location!.longitude!]}
            pathOptions={{ color: "purple", fillColor: "purple" }}
            radius={500}
            weight={1}
          >
    

            <Popup>
              <pre>
                {JSON.stringify({ ...item, availability: [] }, null, 2)}
              </pre>
            </Popup>
          </Circle>
        ))}
        {sc?.appointmentList?.map((item) => (
          <Polyline
            weight={2}
            pathOptions={{
              color:
                howFar(item) > (item.therapist?.maxTravelDistanceKm ?? 0)
                  ? "red"
                  : "blue",
            }}
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
          >
            <Tooltip className="tt" direction="top">
              <p>{item.patient?.name + " --> " + item.therapist?.name}</p>
              <div>
                {Object.entries(
                  (item.patient?.availability ?? []).reduce((acc: IMap, x) => {
                    const date = dayjs(x!.date!).format("DD MMM,YYYY");
                    if (!acc[date]) {
                      acc[date] = [];
                    }
                    acc[date].push(x!.startTime!);
                    return acc;
                  }, {} as IMap)
                ).map(([date, times]) => (
                  <div key={date}>
                    <p
                      style={{
                        fontWeight:
                          dayjs(item.timeslot?.date).format("DD MMM,YYYY") ===
                          date
                            ? "bold"
                            : "normal",
                      }}
                    >
                      {dayjs(item.timeslot?.date).format("DD MMM,YYYY") ===
                      date ? (
                        <span>✅</span>
                      ) : (
                        <span>🚫</span>
                      )}
                      &nbsp;{date}
                    </p>
                    <div
                      style={{ width: 300, display: "flex", flexWrap: "wrap" }}
                    > 
                      {times.map((time, index) => (
                        <Chip
                          mr={2}
                          color={
                            dayjs(item.timeslot?.date).format("DD MMM,YYYY") ===
                              date &&
                            JSON.stringify(time) ===
                              JSON.stringify(item.timeslot?.startTime)
                              ? "green"
                              : "red"
                          }
                          key={index}
                        >
                          <small>{(time as string).substring(0, 5)}</small>
                        </Chip>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
              <p>{item.patient?.therapyType}</p>
              <p>
                {dayjs(item.timeslot?.date).format("DD MMM,YYYY")} at{" "}
                {String(item.timeslot?.startTime)}
              </p>

              <p
                style={{
                  color:
                    howFar(item) > (item.therapist?.maxTravelDistanceKm ?? 0)
                      ? "red"
                      : "green",
                }}
              >
                Therapist Max Travel{" = "}
                {item.therapist?.maxTravelDistanceKm + " km"}
                <b>
                  {" "}
                  {howFar(item) > (item.therapist?.maxTravelDistanceKm ?? 0)
                    ? "🚫"
                    : "✅"}
                  &nbsp;{howFar(item).toFixed(2) + " km"}
                </b>
              </p>
            </Tooltip>
          </Polyline>
        ))}
      </MapContainer>  
      <table border={1} style={{ outline: "none" }}>
        <thead>
          <tr>
            <td>Constraint</td>
            <td>Type</td>
            <td>Hard/Soft</td>
            <td>Justification</td>
            <td>Appointment</td>
          </tr>
        </thead>
        <tbody>
          {Object.values(api.data?.scoreAnalysis?.constraintMap ?? {}).map(
            (c) => {
           
              return (
                <tr>
                  <td>{c.constraintRef?.constraintName}</td>
                  <td>{makeTable(c.score)}</td>
                  <td>{c.weight?.hardScore === 0 ? "soft" : "hard"}</td>

                  <td>{c.matches?.map(x => {
                    return (<div key={String(x)}>
                      {makeTable(x.justification?.["impact"])}
                    
                    </div>);
                  })}</td>
                
                <td>{c.matches?.map(x => {
                     const fact:Appointment=  x.justification?.["facts"][0];
                    return (<div key={String(x)}>
                    
                      <p>{fact.patient?.name} ----- {fact.therapist?.name??"N/A"}</p>
                      <small>{fact.timeslot?.date??"N/A"} {String(fact.timeslot?.startTime??"-")}</small>
                    </div>);
                  })}</td>
                </tr>
              );
            }
          )}
        </tbody>
      </table>
      <pre>{JSON.stringify(api.data?.scoreAnalysis, null, 2)}</pre>

      <table border={1} style={{ outline: "none" }}>
        <thead>
          <tr>
            <td>Patient</td>
            <td>Therapist</td>
            <td>Therapy Type</td>
            <td>Appointment</td>
          </tr>
        </thead>
        <tbody>
          {api.data &&
            sc?.appointmentList?.map((item) => (
              <tr key={item.id}>
                <td>
                  <p>
                    {item.patient?.name} ({item.patient?.therapyType})
                  </p>
                  <div style={{ width: 500, padding: "10px" }}>
                    {Object.entries(
                      (item.patient?.availability ?? []).reduce(
                        (acc: IMap, x) => {
                          const date = dayjs(x!.date!).format("DD MMM,YYYY");
                          if (!acc[date]) {
                            acc[date] = [];
                          }
                          acc[date].push(x!.startTime!);
                          return acc;
                        },
                        {} as IMap
                      )
                    ).map(([date, times]) => (
                      <div key={date}>
                        {item.timeslot ? (
                          <p
                            style={{
                              fontWeight:
                                dayjs(item.timeslot?.date).format(
                                  "DD MMM,YYYY"
                                ) === date
                                  ? "bold"
                                  : "normal",
                            }}
                          >
                            {dayjs(item.timeslot?.date).format(
                              "DD MMM,YYYY"
                            ) === date ? (
                              <span>✅</span>
                            ) : (
                              <span>🚫</span>
                            )}
                            &nbsp;{date}
                          </p>
                        ) : (
                          "N/A"
                        )}
                        <div
                          style={{
                            width: 300,
                            display: "flex",
                            flexWrap: "wrap",
                          }}
                        >
                          {item.timeslot &&
                            times.map((time, index) => (
                              <Chip
                                mr={0.5}
                                color={
                                  dayjs(item.timeslot?.date).format(
                                    "DD MMM,YYYY"
                                  ) === date &&
                                  JSON.stringify(time) ===
                                    JSON.stringify(item.timeslot?.startTime)
                                    ? "green"
                                    : "red"
                                }
                                key={index}
                              >
                                <small>
                                  {(time as string).substring(0, 5)}
                                </small>
                              </Chip>
                            ))}
                        </div>
                      </div>
                    ))}
                  </div>
                </td>
                <td>
                  <p>
                    {item.therapist?.name}({item.therapist?.skills?.join(", ")})
                  </p>
                  <div style={{ width: 500, padding: "10px" }}>
                    {Object.entries(
                      (item.therapist?.availability ?? []).reduce(
                        (acc: IMap, x) => {
                          const date = dayjs(x!.date!).format("DD MMM,YYYY");
                          if (!acc[date]) {
                            acc[date] = [];
                          }
                          acc[date].push(x!.startTime!);
                          return acc;
                        },
                        {} as IMap
                      )
                    ).map(([date, times]) => (
                      <div key={date}>
                        <p
                          style={{
                            fontWeight:
                              dayjs(item.timeslot?.date).format(
                                "DD MMM,YYYY"
                              ) === date
                                ? "bold"
                                : "normal",
                          }}
                        >
                          {dayjs(item.timeslot?.date).format("DD MMM,YYYY") ===
                          date ? (
                            <span>✅</span>
                          ) : (
                            <span>🚫</span>
                          )}
                          &nbsp;{date}
                        </p>
                        <div
                          style={{
                            width: 300,
                            display: "flex",
                            flexWrap: "wrap",
                          }}
                        >
                          {times.map((time, index) => (
                            <Chip
                              mr={1}
                              color={
                                dayjs(item.timeslot?.date).format(
                                  "DD MMM,YYYY"
                                ) === date &&
                                JSON.stringify(time) ===
                                  JSON.stringify(item.timeslot?.startTime)
                                  ? "green"
                                  : "red"
                              }
                              key={index}
                            >
                              <small>{(time as string).substring(0, 5)}</small>
                            </Chip>
                          ))}
                        </div>
                      </div>
                    ))}
                  </div>
                </td>

                <td>{item.patient?.therapyType}</td>
                <td>{dayjs(item.timeslot?.date).format("DD MMM,YYYY")}</td>
              </tr>
            ))}
        </tbody>
      </table>
    </Box>
  );

  function howFar(item: Appointment) {
    return haversineDistance(
      {
        latitude: item.therapist!.location!.latitude!,
        longitude: item.therapist!.location!.longitude!,
      },
      {
        latitude: item.patient!.location!.latitude!,
        longitude: item.patient!.location!.longitude!,
      }
    );
  }
}

export default App;

function makeTable(data: any) {
  const keys = Object.keys(data ?? {});
  return (
    <table>
      <tbody>
        {keys.map((key) => (
          <tr>
            <th>{key}</th>
            <td>{String(data[key])}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
