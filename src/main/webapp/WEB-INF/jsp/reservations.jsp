<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, com.teamLead.reservationVoiture.dto.ReservationDto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Liste des réservations</title>
    <style>
        :root{ --accent:#3498db; --muted:#8a8f95; }
        body { font-family: Arial, Helvetica, sans-serif; background: #fafafa; color: #333; margin: 20px; }
        h1 { color: #2c3e50; margin-bottom: 8px; }
        form.filter { margin-bottom: 14px; display:flex; gap:8px; align-items:center; flex-wrap:wrap; }
        label { font-size: 0.95rem; color: var(--muted); }
        input[type="date"] { padding: 6px 8px; border: 1px solid #dcdfe3; border-radius: 4px; }
        button { background: var(--accent); color: #fff; border: none; padding: 8px 12px; border-radius: 4px; cursor: pointer; }
        button:hover { background: #2c80c9; }
        table { border-collapse: collapse; width: 100%; background: #fff; box-shadow: 0 1px 2px rgba(0,0,0,0.04); }
        th, td { border: 1px solid #eee; padding: 10px 12px; }
        th { background: linear-gradient(#f7f9fb,#f2f5f8); color:#21313b; text-align: left; }
        tr:nth-child(even) { background: #fbfcfd; }
        tr:hover { background: #f1f8ff; }
        .arrival-time { font-family: "Courier New", monospace; color: #e67e22; font-weight: 600; }
        .muted { color: var(--muted); font-size: 0.95rem; }
    </style>
</head>
<body>
<h1>Liste des réservations</h1>

<form class="filter" method="get" action="${pageContext.request.contextPath}/front/reservations">
    <label>Date (exacte): <input type="date" name="date" /></label>
    <span class="muted">ou</span>
    <label>Du: <input type="date" name="from" /></label>
    <label>Au: <input type="date" name="to" /></label>
    <button type="submit">Filtrer</button>
</form>

<br/>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Client</th>
        <th>Hôtel</th>
        <th>Date & heure d'arrivée</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<ReservationDto> list = (List<ReservationDto>) request.getAttribute("reservations");
        if (list != null && !list.isEmpty()) {
            for (ReservationDto r : list) {
    %>
        <tr>
            <td><%= r.getId() %></td>
            <td><%= r.getCustomerName() %></td>
            <td><%= r.getHotelName() %></td>
            <td>
                <%
                    java.time.LocalDateTime ldt = null;
                    if (r.getArrivalDateTime() != null) ldt = r.getArrivalDateTime();
                    else if (r.getArrivalDate() != null) ldt = r.getArrivalDate().atStartOfDay();
                    if (ldt != null) {
                        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                %>
                    <span class="arrival-time"><%= ldt.format(fmt) %></span>
                <%
                    } else {
                %>
                    <span class="muted">-</span>
                <%
                    }
                %>
            </td>
        </tr>
    <%      }
        } else { %>
        <tr>
            <td colspan="4">Aucune réservation trouvée.</td>
        </tr>
    <% } %>
    </tbody>
</table>

</body>
</html>
