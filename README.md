# 🎬 MovieVerse: Java Movie Ticketing System with GUI

A modern Java-based Movie Ticketing System with a Swing-based GUI. It supports multi-language movie listings, flexible booking options, PDF ticket generation, email confirmation, real-time seat locking, and Google Sheets integration via API.

---

## ✨ Features

- ✅ **Real-time Google Sheets Data Storage** (via [SheetDB.io](https://sheetdb.io)) with form validation  
- 🌐 **Language Options**: Hindi, Gujarati, English – 3 movies per language  
- 📅 **Flexible Booking Dates**: Book tickets for up to **7 days** in advance  
- 🎭 **Ticket Categories**:
  - Recliner
  - Premium
  - Executive  
- 🎥 **2D/3D Movie Options**  
- 📧 **Email Ticket Confirmation** to users  
- 📄 **PDF Ticket Download**  
- 🔒 **Seat Locking**: Prevents double bookings  
- 🔐 **Admin Dashboard**: View total tickets sold, revenue earned, and more  

---

## 📦 Libraries Used

| Library                             | Description                            | Download Link |
|-------------------------------------|----------------------------------------|----------------|
| `activation-1.1.1`                  | JavaBeans Activation Framework         | [Download](https://mvnrepository.com/artifact/javax.activation/activation/1.1.1) |
| `apache.httpcomponents`            | Apache HttpClient for HTTP calls       | [Download](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient) |
| `google.api.client`                | Google API Client Library              | [Download](https://mvnrepository.com/artifact/com.google.api-client/google-api-client) |
| `google.apis.api.services`         | Google APIs Services                   | [Download](https://mvnrepository.com/artifact/com.google.apis/google-api-services-sheets) |
| `google.oauth.client.jetty`        | OAuth client with Jetty support        | [Download](https://mvnrepository.com/artifact/com.google.oauth-client/google-oauth-client-jetty) |
| `itextpdf-5.5.13.2`                | PDF generation library                 | [Download](https://mvnrepository.com/artifact/com.itextpdf/itextpdf/5.5.13.2) |
| `javax.mail-1.6.2`                 | JavaMail API for sending emails        | [Download](https://mvnrepository.com/artifact/com.sun.mail/javax.mail/1.6.2) |
| `jcalendar-1.4`                    | Calendar date picker for Swing         | [Download](https://mvnrepository.com/artifact/net.sourceforge.jcalendar/jcalendar/1.4) |
| `json`                             | JSON Parsing in Java                   | [Download](https://mvnrepository.com/artifact/org.json/json) |
| `konghq.unirest.java`              | Lightweight HTTP request library       | [Download](https://mvnrepository.com/artifact/com.konghq/unirest-java) |
| `slf4j-nop-1.7.30`                 | No-operation SLF4J binding             | [Download](https://mvnrepository.com/artifact/org.slf4j/slf4j-nop/1.7.30) |
| `slf4j.simple`                     | Simple SLF4J implementation            | [Download](https://mvnrepository.com/artifact/org.slf4j/slf4j-simple) |

---

## 🛠 Tech Stack

- Java Swing (GUI)
- Google Sheets API (via SheetDB)
- iText (PDF)
- JavaMail API
- SLF4J Logging
- Apache HttpClient & Unirest

