CREATE VIEW room_capacity_by_hotel AS
SELECT hotel_id, SUM(room_capacity) AS total_capacity
FROM Room
GROUP BY hotel_id;

CREATE VIEW available_rooms_per_area AS
SELECT H.area, COUNT(*) AS num_available_rooms
FROM Hotel H
INNER JOIN Room R ON H.hotel_id = R.hotel_id
LEFT JOIN Booking B ON R.room_id = B.room_id
WHERE B.room_id IS NULL
GROUP BY H.area;

