# OTT — Overhead Transmission Toolbox

**OTT** is a comprehensive engineering suite for high-voltage overhead transmission line engineers, available both as a native Android application and as a web application hosted on **GitHub Pages**.

---

## ⚡ Live Web App (GitHub Pages)

The repository includes a web version in the `/docs` directory and an automated GitHub Actions deployment workflow.

### Enabling GitHub Pages for your Repo:
1. Push this repository to GitHub.
2. In your GitHub repository, click **Settings** → **Pages** (in the left sidebar).
3. Under **Build and deployment**:
   - **Option A (GitHub Actions)**: Under **Source**, select **GitHub Actions**. The included `.github/workflows/deploy-pages.yml` will automatically deploy the site on every push to `main`!
   - **Option B (Direct Branch)**: Under **Source**, select **Deploy from a branch**, set branch to **`main`**, and folder to **`/docs`**, then click **Save**.
4. Your OTT Web Toolbox will be live at `https://<your-username>.github.io/<your-repo-name>/`.

---

## 📱 Android Application

The native Android app is built using **Kotlin**, **Jetpack Compose (Material 3)**, and Android Architecture Components.

### Features across both Web and Android:
- **Line Design**: Catenary Sag & Tension (with interactive canvas), Ruling Span, Conductor Blowout & Wind Sway, IEEE Std 738 Thermal Ampacity.
- **Corrosion**: Atmospheric Galvanizing Coating Life (ISO 9223 C1–CX), Soil Anchor Pitting Loss (Romanoff model), AC Interference Current Density (ISO 18086), Cathodic Protection Anode Sizing.
- **Inspection & Assessment**: Transmission Defect Priority Scoring Matrix (EPRI DSI), Wood Pole Shell Remaining Strength (ASCE Manual 91 / ANSI O5.1), Lattice Member Buckling (ASCE 10).
- **Lightning & Grounding**: Tower Footing Grounding Resistance (IEEE Std 80/142) with high-current soil ionization, Shielding Failure & Strike Angle (EGM), Critical Backflashover Current & Trip Rate, Wenner 4-Point Soil Profiler.
- **Conductor & Hardware**: Standard Conductor Database (Hawk, Drake, Cardinal, Curlew, Finch, ACSS, etc.), Aeolian Vibration & Stockbridge Damper Placement, Insulator Creepage Distance (IEC 60815).
- **Right-of-Way & Environment**: Ground EMF Profiler, ROW Corridor Minimum Width (NESC Rule 234), Corona Loss & Rain Audible Noise (EPRI).
- **Standards Glossary**: 20+ terms cross-referenced with IEEE, NESC, ASCE, CIGRE, ISO, and IEC standards.
