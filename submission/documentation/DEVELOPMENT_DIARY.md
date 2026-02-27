# DEVELOPMENT DIARY
## Vegetable Service Engine — Java RMI + Servlet Project
*February 14 – 27, 2026 | 14-Day Build Log*

---

## Day 1: February 14, 2026
### Project Kickoff & Environment Setup

#### Planning & Setup
- Reviewed project brief and outlined scope: Java RMI + Servlet vegetable service engine.
- Sketched architecture diagram: RMI layer → Servlet layer → Web front-end.
- Created project directory structure: `src/rmi`, `src/servlet`, `src/client`.
- Set up Java development environment and verified JDK installation.

#### Dependency Research
- Researched Apache Tomcat versions; selected Tomcat 9.0.93 for servlet compatibility.
- Downloaded `javax.servlet-api-4.0.1.jar` dependency.
- Read Java RMI documentation and reviewed example projects.
- Documented planned class list: `Task`, `Compute`, `VegetableComputeEngine`, and task implementations.

---

## Day 2: February 15, 2026
### RMI Interface Design

#### Core Interfaces
- Created `Task.java` interface with generic type parameter `<T>`.
- Implemented `Compute.java` remote interface extending `java.rmi.Remote`.
- Wrote Javadoc comments for both interfaces.
- Tested compilation of interface classes successfully.

#### Engine Skeleton
- Began `VegetableComputeEngine.java` — extended `UnicastRemoteObject`.
- Implemented `Compute` interface stub.
- Configured RMI registry on port 1099.

> **Challenge:** Port 1099 already occupied from a previous test run.  
> **Solution:** Used `pkill -f VegetableComputeEngine` to kill stale process.

---

## Day 3: February 16, 2026
### RMI Engine & Initial Task Classes

#### Engine Completion
- Finalized `VegetableComputeEngine.java` with full HashMap operations.
- Added initial seed data: tomato, onion, potato.
- Verified engine starts cleanly and registers with RMI registry.

#### First Task Implementations
- Implemented `AddVegetablePrice.java` — adds new vegetables to the map.
- Implemented `UpdateVegetablePrice.java` — updates existing prices.
- Implemented `DeleteVegetablePrice.java` — removes vegetables by name.
- Tested each task with print statements; all returned expected output.

> **Challenge:** NullPointerException when attempting to update a non-existent vegetable.  
> **Solution:** Added null checks and descriptive error messages before any map access.

---

## Day 4: February 17, 2026
### Cost Calculation Tasks

#### Single-Item Cost
- Implemented `CalVegetableCost.java` — calculates cost for a single item given quantity.
- Added input validation: negative quantities and unknown names handled gracefully.
- Unit tested with multiple vegetable and quantity combinations.

#### Receipt Generator
- Implemented `CalculateCost.java` — generates a formatted receipt with itemised totals and change.
- Created `ListAllVegetables.java` — displays formatted inventory table.
- Ran integration test across all five task classes; all passed.

---

## Day 5: February 18, 2026
### Client Registry & RMI Testing

#### Client Registry
- Created `VegetableComputeTaskRegistry.java` to centralise RMI lookup logic.
- Developed `TestRMIClient.java` for command-line smoke testing.
- Compiled all RMI classes: `javac -d classes src/rmi/*.java`.

> **Challenge:** Connection refused errors when running `TestRMIClient`.  
> **Solution:** Added `-Djava.rmi.server.hostname=localhost` JVM flag to server startup.

#### Full RMI Validation
- Ran all six operations via `TestRMIClient` — all returned correct responses.
- Documented RMI startup procedure in project README.
- Committed stable RMI layer to version control.

---

## Day 6: February 19, 2026
### Servlet Layer — Design & Init

#### Servlet Architecture
- Planned servlet action routing: six actions mapped via switch-case.
- Created `VegetableServiceServlet.java` skeleton with `HttpServlet` extension.
- Implemented `init()` lifecycle method to establish RMI connection on startup.

#### doPost Implementation
- Implemented `doPost()` with switch-case routing for all six actions.
- Added try-catch blocks around each RMI call for graceful error responses.
- Created `web.xml` deployment descriptor and configured servlet mapping to `/vegetable`.

> **Challenge:** `Servlet init()` could not resolve RMI server using `'localhost'`.  
> **Solution:** Changed registry lookup hostname to `127.0.0.1` — resolved immediately.

---

## Day 7: February 20, 2026
### WAR Packaging & Tomcat Deployment

#### WAR Build
- Structured WAR layout: `WEB-INF/classes`, `WEB-INF/lib`, `web.xml`.
- Packaged `vegetable-service.war` using `jar` command.
- Verified WAR integrity by inspecting contents.

#### Tomcat Deploy & Smoke Test
- Deployed WAR to Tomcat 9 webapps directory.
- Started Tomcat; confirmed successful deployment in logs.
- Tested all six servlet actions with curl commands — all responses correct.
- Resolved Tomcat port conflict by switching to port 8090.

> **Challenge:** Default Tomcat port 8080 conflicted with another local service.  
> **Solution:** Updated `server.xml` connector port to `8090`.

---

## Day 8: February 21, 2026
### Initial Web Interface

#### HTML Foundation
- Created `index.html` with Bootstrap 5 grid layout.
- Added form sections for all six servlet actions.
- Styled with basic CSS — clean, functional, no frills.

#### JavaScript Integration
- Implemented `fetch()` API calls for each form submission.
- Parsed JSON responses and displayed results in result panels.
- Tested all six operations end-to-end through the browser — all passed.

> **Challenge:** Browser caching served stale JS after updates.  
> **Solution:** Used hard refresh (`Ctrl+Shift+R`) and added cache-busting query params during dev.

---

## Day 9: February 22, 2026
### Role-Based System Design

#### UX Architecture
- Identified two distinct user personas: Manager (inventory) and Cashier (POS).
- Designed separate portals with tailored feature sets for each role.
- Created wireframes for `manager.html` and `cashier.html`.

#### Portal Pages
- Built `manager.html` — full CRUD for vegetable inventory.
- Built `cashier.html` — price lookup, cost calculation, receipt generation.
- Created landing page (`index.html`) for role selection.

---

## Day 10: February 23, 2026
### Authentication System

#### Login Page
- Built `login.html` with username/password form.
- Implemented JavaScript credential validation against demo credentials.
- Demo credentials set: Manager (`admin` / `admin123`), Cashier (`cashier` / `cash123`).

#### Session Management
- Implemented `sessionStorage` for maintaining login state across pages.
- Added session guard on page load for both manager and cashier portals.
- Implemented logout functionality clearing session and redirecting to login.

> **Challenge:** Users could navigate directly to portals without logging in.  
> **Solution:** Added JavaScript session check at the top of each portal's `onload` handler — redirects to login if no session found.

---

## Day 11: February 24, 2026
### Professional UI Styling

#### Visual Design
- Applied gradient backgrounds to all pages for a polished look.
- Designed card-based layouts for form sections and results.
- Added role badge and logged-in username display to both portals.

#### UX Refinements
- Added confirmation dialogs before delete operations.
- Implemented auto-refresh of inventory table after any add, update, or delete.
- Standardised button styles and spacing across all pages.

---

## Day 12: February 25, 2026
### Security & Error Handling

#### Secure Error Responses
- Audited all servlet catch blocks — replaced stack trace output with sanitised JSON error messages.
- Added HTML detection in JavaScript to intercept Tomcat HTML error pages and display user-friendly messages instead.
- Verified no internal server paths or class names exposed in any error response.

> **Challenge:** Tomcat default error pages returned full HTML with internal details.  
> **Solution:** Added HTML content-type detection in `fetch()` response handler to substitute a generic error message.

#### Security Hardening
- Reviewed all input handling for potential injection issues.
- Added server-side validation for vegetable name and price fields.
- Confirmed role-based access prevents cross-role operations at UI level.

---

## Day 13: February 26, 2026
### Final Testing & Bug Fixes

#### End-to-End Testing
- Ran full test suite across all six operations via browser and curl.
- Tested both Manager and Cashier flows from login through logout.
- Identified and fixed 12 bugs including: null price edge case, session not cleared on direct logout, and receipt rounding error.

#### Code Cleanup
- Added comprehensive Javadoc and inline comments across all Java classes.
- Cleaned up unused imports and dead code.
- Final WAR rebuild and clean Tomcat redeploy — all tests passed.

---

## Day 14: February 27, 2026
### Documentation & Project Wrap-Up

#### Documentation
- Wrote project README with setup instructions, startup sequence, and demo credentials.
- Compiled this development diary summarising all 14 days.
- Noted key learnings: Java RMI, Servlet lifecycle, AJAX/Fetch, `sessionStorage`, error handling.

#### Final Review
- Final statistics: 10 hours active coding, 1,270 lines of code, 12 bugs fixed, 4 cups of coffee.
- Confirmed all required features implemented plus role-based access as an enhancement.
- Project submitted. All tests passing. Development complete.

---

## Key Learnings

### Technical Skills Gained
- **Java RMI:** Remote method invocation, registry, and stub generation.
- **Servlet Development:** HTTP request handling, `init()` lifecycle, WAR deployment.
- **Session Management:** Browser `sessionStorage` for authentication state.
- **AJAX / Fetch API:** Asynchronous HTTP requests from JavaScript.
- **Error Handling:** Graceful, user-facing error messages without exposing internals.

### Design Patterns Applied
- **Strategy Pattern:** `Task` interface with multiple concrete implementations.
- **Proxy Pattern:** RMI acting as a transparent remote proxy.
- **MVC Pattern:** Clean separation of concerns across three tiers.
- **Factory Pattern:** `TaskRegistry` creating and executing tasks on demand.

### Challenges Overcome
- RMI hostname configuration (`localhost` vs `127.0.0.1`).
- Tomcat port conflicts — resolved by switching to port 8090.
- WAR packaging and correct `WEB-INF` structure.
- Browser caching during front-end development.
- Session management and direct URL access protection.
- Secure error handling to prevent internal detail leakage.

