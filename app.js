// OTT - Overhead Transmission Toolbox (Web Engine for GitHub Pages)

const CONDUCTORS = [
  { codeWord: "Hawk", type: "ACSR", kcmil: 477.0, stranding: "26/7", diamIn: 0.858, wtLbFt: 0.655, rbsLbf: 19500, r75: 0.219, amp: 659 },
  { codeWord: "Drake", type: "ACSR", kcmil: 795.0, stranding: "26/7", diamIn: 1.108, wtLbFt: 1.094, rbsLbf: 31500, r75: 0.139, amp: 907 },
  { codeWord: "Cardinal", type: "ACSR", kcmil: 954.0, stranding: "54/7", diamIn: 1.196, wtLbFt: 1.226, rbsLbf: 33800, r75: 0.117, amp: 1014 },
  { codeWord: "Curlew", type: "ACSR", kcmil: 1033.5, stranding: "54/7", diamIn: 1.246, wtLbFt: 1.328, rbsLbf: 36300, r75: 0.108, amp: 1063 },
  { codeWord: "Bluejay", type: "ACSR", kcmil: 1113.0, stranding: "45/7", diamIn: 1.259, wtLbFt: 1.254, rbsLbf: 30700, r75: 0.103, amp: 1109 },
  { codeWord: "Finch", type: "ACSR", kcmil: 1113.0, stranding: "54/19", diamIn: 1.293, wtLbFt: 1.431, rbsLbf: 39100, r75: 0.102, amp: 1115 },
  { codeWord: "Partridge", type: "ACSR", kcmil: 266.8, stranding: "26/7", diamIn: 0.642, wtLbFt: 0.367, rbsLbf: 11300, r75: 0.385, amp: 475 },
  { codeWord: "Penguin", type: "ACSR", kcmil: 211.6, stranding: "6/1", diamIn: 0.563, wtLbFt: 0.291, rbsLbf: 8290, r75: 0.442, amp: 430 },
  { codeWord: "Grosbeak", type: "ACSR", kcmil: 636.0, stranding: "26/7", diamIn: 0.990, wtLbFt: 0.875, rbsLbf: 25200, r75: 0.174, amp: 786 },
  { codeWord: "Rail", type: "ACSR", kcmil: 954.0, stranding: "45/7", diamIn: 1.165, wtLbFt: 1.075, rbsLbf: 26300, r75: 0.120, amp: 990 },
  { codeWord: "Drake/ACSS", type: "ACSS", kcmil: 795.0, stranding: "26/7", diamIn: 1.108, wtLbFt: 1.094, rbsLbf: 31500, r75: 0.139, amp: 1420 },
  { codeWord: "Cardinal/ACSS", type: "ACSS", kcmil: 954.0, stranding: "54/7", diamIn: 1.196, wtLbFt: 1.226, rbsLbf: 33800, r75: 0.117, amp: 1590 },
  { codeWord: "Greeley", type: "AAAC", kcmil: 927.2, stranding: "37", diamIn: 1.108, wtLbFt: 0.868, rbsLbf: 28200, r75: 0.119, amp: 930 }
];

const GLOSSARY_TERMS = [
  { term: "Catenary Curve", std: "IEEE Std 738 / ASCE 74", cat: "Line Design", def: "The mathematical hyperbolic cosine curve assumed by a flexible cable hanging freely under its own uniform weight.", ctx: "Calculates mid-span sag, clearance to ground datum, and maximum tension under ice/wind." },
  { term: "Ruling Span (Equivalent Span)", std: "NESC Section 25 / CIGRE SC22", cat: "Line Design", def: "A hypothetical single level span whose tension responds to temperature and wind in the same manner as the average span in a dead-end section.", ctx: "Calculated as S_r = sqrt(sum(S_i^3)/sum(S_i)). Tension equalizes across suspension insulators." },
  { term: "Blowout Angle", std: "NESC Rule 234 / ASCE 74", cat: "Line Design", def: "Angular displacement of a conductor from the vertical under transverse wind pressure.", ctx: "Governs right-of-way width requirements and edge-of-corridor danger tree clearances." },
  { term: "Conductor Creep", std: "Aluminum Association", cat: "Line Design", def: "Permanent non-elastic elongation of stranded aluminum conductors occurring under sustained mechanical tension over decades.", ctx: "Line designers account for 10-year creep when calculating 50-year clearance margins." },
  { term: "Atmospheric Corrosivity (C1-CX)", std: "ISO 9223 / ASTM G92", cat: "Corrosion", def: "International environmental classification quantifying atmospheric metal loss based on salinity, SO2, and time of wetness.", ctx: "Determines hot-dip galvanizing zinc depletion rates (0.1 um/yr in C1 up to >8.4 um/yr in C5/CX)." },
  { term: "Romanoff Soil Corrosion Model", std: "NIST Circular 579", cat: "Corrosion", def: "Empirical power-law model P = k * t^n estimating underground penetration depth of carbon steel in soil.", ctx: "Used to assess remaining thickness of buried guy anchor rods and grillage steel." },
  { term: "AC Induced Corrosion", std: "ISO 18086 / NACE SP0106", cat: "Corrosion", def: "Rapid localized pitting driven by AC current discharge into soil induced by adjacent high-voltage lines.", ctx: "Discharge densities exceeding 100 A/m2 create rapid perforation risk." },
  { term: "Cathodic Protection (CP)", std: "NACE SP0169 / IEEE 1695", cat: "Corrosion", def: "Electrochemical protection technique making steel the cathode using sacrificial magnesium or zinc anodes.", ctx: "Applied to buried transmission tower grillages and guy anchors." },
  { term: "Transmission Defect Priority (DSI)", std: "EPRI Transmission Guidelines", cat: "Inspection and Assessment", def: "Structured risk matrix categorizing structure, conductor, and insulator defects from P1 (emergency <24h) to P5 (monitor).", ctx: "Ensures critical public safety hazards trigger immediate clearance." },
  { term: "Wood Pole Sounding & Boring", std: "ANSI O5.1 / ASCE Manual 91", cat: "Inspection and Assessment", def: "Non-destructive acoustic hammer sounding and resistance core boring to evaluate sound wood shell thickness.", ctx: "NESC mandates replacement or C-Truss splinting if remaining moment capacity drops below 67%." },
  { term: "Slenderness Ratio (KL/r)", std: "ASCE 10 / AISC 360", cat: "Inspection and Assessment", def: "Ratio of effective unbraced buckling length KL to minimum radius of gyration r.", ctx: "ASCE 10 sets maximum KL/r <= 200 for transmission tower compression leg members." },
  { term: "Backflashover (BFOR)", std: "IEEE Std 1243 / CIGRE SC33", cat: "Lightning and Grounding", def: "Insulator flashover when lightning strikes the shield wire or tower top, elevating tower voltage due to footing resistance.", ctx: "Dominant cause of lightning trip-outs; controlled by lowering footing resistance below 10-15 ohms." },
  { term: "Shielding Failure (SFFOR)", std: "IEEE Std 1243 / IEEE 1410", cat: "Lightning and Grounding", def: "Direct strike terminating on a phase conductor rather than the overhead shield wire.", ctx: "Evaluated using the Electrogeometric Model (EGM) to ensure shielding angle <= 30 deg." },
  { term: "Wenner 4-Point Soil Method", std: "IEEE Std 81", cat: "Lightning and Grounding", def: "Geophysical test using 4 equally spaced ground pins to calculate apparent resistivity rho = 2 * pi * a * R.", ctx: "Establishes multi-layer soil stratification for grounding design." },
  { term: "Aeolian Vibration & Damper", std: "IEEE Std 664 / CIGRE SC22", cat: "Conductor and Hardware", def: "High-frequency vortex-shedding vibrations causing fatigue failure of strands at suspension clamps.", ctx: "Damped using Stockbridge dampers positioned at 0.8 * half-loop length from the clamp." },
  { term: "Insulator Creepage Distance", std: "IEC 60815 / IEEE 1313", cat: "Conductor and Hardware", def: "Shortest surface path along an insulator shed between energized and grounded hardware.", ctx: "Mandates minimum leakage distance (25-31 mm/kV) to prevent pollution flashover." },
  { term: "Electric & Magnetic Fields (EMF)", std: "IEEE Std 644 / ICNIRP", cat: "Right-of-Way and Environment", def: "Low-frequency fields produced by line voltage (kV/m) and line current (mG / uT).", ctx: "Engineers calculate lateral ground profiles to verify compliance with ICNIRP public exposure limits." },
  { term: "Audible Noise (Wet Corona)", std: "EPRI Reference Book / IEEE 656", cat: "Right-of-Way and Environment", def: "Acoustic frying and crackling sound generated by ionization discharges at water droplets adhering to conductors.", ctx: "Predominant at 345 kV+ during rain or fog; mitigated using bundled conductors." }
];

// App State
let currentCategory = "line_design";
let currentTool = "catenary";
let unitSystem = "US"; // US or SI

document.addEventListener("DOMContentLoaded", () => {
  setupCategoryNavigation();
  setupUnitToggle();
  setupGlobalSearch();
  renderCurrentView();
});

function setupUnitToggle() {
  const btn = document.getElementById("unitToggleBtn");
  btn.addEventListener("click", () => {
    unitSystem = unitSystem === "US" ? "SI" : "US";
    btn.textContent = `Units: ${unitSystem}`;
    renderCurrentView();
  });
}

function setupCategoryNavigation() {
  const tabs = document.querySelectorAll(".category-nav .nav-tab");
  tabs.forEach(tab => {
    tab.addEventListener("click", () => {
      tabs.forEach(t => t.classList.remove("active"));
      tab.classList.add("active");
      currentCategory = tab.dataset.category;
      
      // Default tool for selected category
      const defaults = {
        line_design: "catenary",
        corrosion: "zinc_life",
        inspection: "defect_matrix",
        lightning: "footing_resistance",
        conductor: "conductor_db",
        row_env: "emf_profile",
        glossary: "all"
      };
      currentTool = defaults[currentCategory] || "catenary";
      renderCurrentView();
    });
  });
}

function setupGlobalSearch() {
  const searchInput = document.getElementById("globalSearchInput");
  searchInput.addEventListener("input", (e) => {
    const query = e.target.value.trim().toLowerCase();
    if (query.length > 0) {
      renderSearchResults(query);
    } else {
      renderCurrentView();
    }
  });
}

function renderCurrentView() {
  const contentArea = document.getElementById("contentArea");
  if (currentCategory === "glossary") {
    renderGlossaryView(contentArea);
    return;
  }

  // Render Category Sub-tabs & Workspace
  const categoryConfig = {
    line_design: {
      title: "Line Design",
      desc: "Catenary sag, ruling span, blowout sway & ampacity",
      tools: [
        { id: "catenary", label: "Catenary Sag" },
        { id: "ruling_span", label: "Ruling Span" },
        { id: "blowout", label: "Blowout & Sway" },
        { id: "ampacity", label: "IEEE 738 Ampacity" }
      ]
    },
    corrosion: {
      title: "Corrosion Engineering",
      desc: "Atmospheric zinc loss, soil anchors & AC interference",
      tools: [
        { id: "zinc_life", label: "Zinc Galvanizing" },
        { id: "soil_anchor", label: "Soil Anchor Loss" },
        { id: "ac_corrosion", label: "AC Interference" },
        { id: "cp_anode", label: "Cathodic Protection" }
      ]
    },
    inspection: {
      title: "Inspection & Assessment",
      desc: "Defect priority matrix, pole sounding & member buckling",
      tools: [
        { id: "defect_matrix", label: "Defect Priority Matrix" },
        { id: "wood_pole", label: "Wood Pole Shell" },
        { id: "lattice_buckling", label: "Lattice Buckling" }
      ]
    },
    lightning: {
      title: "Lightning & Grounding",
      desc: "Footing impedance, shielding failure & backflashover",
      tools: [
        { id: "footing_resistance", label: "Footing Grounding" },
        { id: "shielding_angle", label: "Shielding Angle" },
        { id: "backflashover", label: "Backflashover Rate" },
        { id: "wenner_test", label: "Wenner 4-Pin Test" }
      ]
    },
    conductor: {
      title: "Conductor & Hardware",
      desc: "ACSR database, Aeolian dampers & insulator creepage",
      tools: [
        { id: "conductor_db", label: "Conductor Database" },
        { id: "aeolian_vibration", label: "Aeolian Damper" },
        { id: "insulator_string", label: "Insulator Creepage" }
      ]
    },
    row_env: {
      title: "Right-of-Way & Environment",
      desc: "Ground EMF profiler, ROW corridor width & corona noise",
      tools: [
        { id: "emf_profile", label: "EMF Profiler" },
        { id: "row_width", label: "ROW Corridor Width" },
        { id: "corona_noise", label: "Corona & Noise" }
      ]
    }
  }[currentCategory];

  let html = `
    <div class="category-hero">
      <div class="hero-info">
        <h2>${categoryConfig.title}</h2>
        <p>${categoryConfig.desc}</p>
      </div>
    </div>
    <div class="sub-nav">
      ${categoryConfig.tools.map(t => `
        <button class="sub-tab-btn ${currentTool === t.id ? 'active' : ''}" onclick="selectSubTool('${t.id}')">
          ${t.label}
        </button>
      `).join('')}
    </div>
    <div id="toolWorkspace"></div>
  `;

  contentArea.innerHTML = html;
  renderToolWorkspace(document.getElementById("toolWorkspace"));
}

window.selectSubTool = function(toolId) {
  currentTool = toolId;
  const subBtns = document.querySelectorAll(".sub-tab-btn");
  subBtns.forEach(btn => btn.classList.remove("active"));
  const activeBtn = Array.from(subBtns).find(b => b.getAttribute("onclick")?.includes(toolId));
  if (activeBtn) activeBtn.classList.add("active");
  renderToolWorkspace(document.getElementById("toolWorkspace"));
};

function renderToolWorkspace(container) {
  if (currentTool === "catenary") renderCatenaryTool(container);
  else if (currentTool === "ruling_span") renderRulingSpanTool(container);
  else if (currentTool === "blowout") renderBlowoutTool(container);
  else if (currentTool === "ampacity") renderAmpacityTool(container);
  else if (currentTool === "zinc_life") renderZincLifeTool(container);
  else if (currentTool === "soil_anchor") renderSoilAnchorTool(container);
  else if (currentTool === "ac_corrosion") renderAcCorrosionTool(container);
  else if (currentTool === "cp_anode") renderCpAnodeTool(container);
  else if (currentTool === "defect_matrix") renderDefectMatrixTool(container);
  else if (currentTool === "wood_pole") renderWoodPoleTool(container);
  else if (currentTool === "lattice_buckling") renderLatticeBucklingTool(container);
  else if (currentTool === "footing_resistance") renderFootingTool(container);
  else if (currentTool === "shielding_angle") renderShieldingTool(container);
  else if (currentTool === "backflashover") renderBackflashoverTool(container);
  else if (currentTool === "wenner_test") renderWennerTool(container);
  else if (currentTool === "conductor_db") renderConductorDbTool(container);
  else if (currentTool === "aeolian_vibration") renderAeolianTool(container);
  else if (currentTool === "insulator_string") renderInsulatorTool(container);
  else if (currentTool === "emf_profile") renderEmfTool(container);
  else if (currentTool === "row_width") renderRowWidthTool(container);
  else if (currentTool === "corona_noise") renderCoronaTool(container);
}

// ======================== TOOL IMPLEMENTATIONS ========================

// 1. Catenary Sag Tool
function renderCatenaryTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Catenary Inputs</h3>
        <p class="panel-desc">Level span catenary equations per IEEE Std 738 & NESC</p>
        <div class="canvas-wrapper">
          <canvas id="catenaryCanvas"></canvas>
        </div>
        <div class="form-group">
          <label>Span Length</label>
          <div class="input-with-unit">
            <input type="number" id="catSpan" value="800">
            <span class="input-unit">ft</span>
          </div>
          <div class="presets">
            <span class="preset-chip" onclick="setVal('catSpan', 400)">400 ft</span>
            <span class="preset-chip" onclick="setVal('catSpan', 800)">800 ft</span>
            <span class="preset-chip" onclick="setVal('catSpan', 1200)">1200 ft</span>
          </div>
        </div>
        <div class="form-group">
          <label>Conductor Weight (w)</label>
          <div class="input-with-unit">
            <input type="number" id="catWeight" value="1.094" step="0.001">
            <span class="input-unit">lb/ft</span>
          </div>
          <div class="presets">
            <span class="preset-chip" onclick="setVal('catWeight', 1.094)">Drake (1.094)</span>
            <span class="preset-chip" onclick="setVal('catWeight', 0.655)">Hawk (0.655)</span>
          </div>
        </div>
        <div class="form-group">
          <label>Horizontal Tension (H)</label>
          <div class="input-with-unit">
            <input type="number" id="catTension" value="6300">
            <span class="input-unit">lbf</span>
          </div>
        </div>
        <div class="form-group">
          <label>Attachment Height above Datum</label>
          <div class="input-with-unit">
            <input type="number" id="catHeight" value="65">
            <span class="input-unit">ft</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Analysis Results</h3>
        <p class="panel-desc">Calculated mid-span sag, clearance, and tension</p>
        <div class="metric-box">
          <div class="metric-header">
            <span>Mid-Span Sag (S)</span>
            <span class="metric-standard">S = C·[cosh(L/2C) - 1]</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="catResSag">--</span>
            <span class="metric-unit">ft</span>
          </div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Ground Clearance at Mid-Span</span>
            <span class="metric-standard">NESC Table 232-1</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="catResClearance">--</span>
            <span class="metric-unit">ft</span>
          </div>
          <div id="catClearanceBadge"></div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Max Tension at Support</span>
            <span class="metric-standard">T = H + w·S</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="catResMaxTension">--</span>
            <span class="metric-unit">lbf</span>
          </div>
          <div class="status-badge pass" id="catTensionPctBadge">Tension: 20% RBS</div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Catenary Constant (C = H / w)</span>
            <span class="metric-standard">Curvature</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="catResC">--</span>
            <span class="metric-unit">ft</span>
          </div>
        </div>
      </div>
    </div>
  `;

  const calculate = () => {
    const span = parseFloat(document.getElementById("catSpan").value) || 800;
    const w = parseFloat(document.getElementById("catWeight").value) || 1.094;
    const h = parseFloat(document.getElementById("catTension").value) || 6300;
    const height = parseFloat(document.getElementById("catHeight").value) || 65;

    const c = h / w;
    const sag = c * (Math.cosh(span / (2 * c)) - 1);
    const maxT = h + (w * sag);
    const clearance = Math.max(0, height - sag);
    const pctRbs = (maxT / 31500) * 100;

    document.getElementById("catResSag").textContent = sag.toFixed(2);
    document.getElementById("catResClearance").textContent = clearance.toFixed(2);
    document.getElementById("catResMaxTension").textContent = Math.round(maxT);
    document.getElementById("catResC").textContent = c.toFixed(1);

    const clBadge = document.getElementById("catClearanceBadge");
    if (clearance >= 25) {
      clBadge.innerHTML = `<span class="status-badge pass">Passes NESC Grade B standard clearance (>25 ft)</span>`;
    } else {
      clBadge.innerHTML = `<span class="status-badge alert">Warning: Clearance below 25 ft threshold</span>`;
    }

    drawCatenaryCanvas(span, sag, clearance);
  };

  ["catSpan", "catWeight", "catTension", "catHeight"].forEach(id => {
    document.getElementById(id).addEventListener("input", calculate);
  });
  calculate();
}

function drawCatenaryCanvas(span, sag, clearance) {
  const canvas = document.getElementById("catenaryCanvas");
  if (!canvas) return;
  const ctx = canvas.getContext("2d");
  const w = canvas.width = canvas.parentElement.clientWidth;
  const h = canvas.height = 180;

  ctx.clearRect(0, 0, w, h);
  const groundY = h - 25;
  const tL = 35;
  const tR = w - 35;
  const tHeight = h - 60;
  const attachY = groundY - tHeight;

  // Ground line
  ctx.strokeStyle = "#415a77";
  ctx.lineWidth = 2;
  ctx.beginPath();
  ctx.moveTo(0, groundY);
  ctx.lineTo(w, groundY);
  ctx.stroke();

  // Towers
  ctx.strokeStyle = "#94a3b8";
  ctx.lineWidth = 3;
  ctx.beginPath();
  ctx.moveTo(tL, groundY); ctx.lineTo(tL, attachY);
  ctx.moveTo(tR, groundY); ctx.lineTo(tR, attachY);
  ctx.stroke();

  // Catenary Curve
  const sagPx = Math.min(tHeight - 20, tHeight * 0.6);
  const midX = (tL + tR) / 2;
  const midY = attachY + sagPx;

  ctx.strokeStyle = "#009cde";
  ctx.lineWidth = 3;
  ctx.beginPath();
  for (let i = 0; i <= 30; i++) {
    const t = i / 30;
    const px = tL + (tR - tL) * t;
    const normX = (px - midX) / ((tR - tL) / 2);
    const py = midY - sagPx * (1 - normX * normX);
    if (i === 0) ctx.moveTo(px, py);
    else ctx.lineTo(px, py);
  }
  ctx.stroke();

  // Labels
  ctx.fillStyle = "#38bdf8";
  ctx.font = "11px monospace";
  ctx.fillText(`Sag: ${sag.toFixed(1)} ft`, midX - 35, midY - 6);
  ctx.fillStyle = "#10b981";
  ctx.fillText(`Clearance: ${clearance.toFixed(1)} ft`, midX - 45, groundY - 6);
}

// 2. Ruling Span Tool
function renderRulingSpanTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Ruling Span Calculator</h3>
        <p class="panel-desc">Equivalent single span for dead-end section tension equilibrium</p>
        <div class="form-group">
          <label>Spans in Dead-End Section (ft, comma-separated)</label>
          <input type="text" id="rulingSpansInput" value="650, 800, 920, 750, 840, 1100">
          <div class="presets">
            <span class="preset-chip" onclick="setVal('rulingSpansInput', '500, 600, 750, 700, 550')">Suburban</span>
            <span class="preset-chip" onclick="setVal('rulingSpansInput', '800, 950, 1100, 1300, 900')">Mountain Section</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Results</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Ruling Span (S_r)</span>
            <span class="metric-standard">S_r = √[Σ(S³) / Σ(S)]</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="rulingResSpan">--</span>
            <span class="metric-unit">ft</span>
          </div>
          <div class="status-badge pass" id="rulingSectionInfo">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const raw = document.getElementById("rulingSpansInput").value;
    const spans = raw.split(",").map(s => parseFloat(s.trim())).filter(n => !isNaN(n) && n > 0);
    if (!spans.length) return;
    const sumCubes = spans.reduce((acc, s) => acc + Math.pow(s, 3), 0);
    const sumSpans = spans.reduce((acc, s) => acc + s, 0);
    const sr = Math.sqrt(sumCubes / sumSpans);

    document.getElementById("rulingResSpan").textContent = sr.toFixed(1);
    document.getElementById("rulingSectionInfo").textContent = `${spans.length} spans • Total length: ${Math.round(sumSpans)} ft`;
  };

  document.getElementById("rulingSpansInput").addEventListener("input", calc);
  calc();
}

// 3. Blowout Tool
function renderBlowoutTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Wind Blowout & Sway Simulator</h3>
        <p class="panel-desc">Calculates transverse conductor swing angle and lateral displacement</p>
        <div class="form-group">
          <label>Conductor Diameter (d)</label>
          <div class="input-with-unit">
            <input type="number" id="blowDiam" value="1.108" step="0.01">
            <span class="input-unit">in</span>
          </div>
        </div>
        <div class="form-group">
          <label>Linear Weight (w)</label>
          <div class="input-with-unit">
            <input type="number" id="blowWt" value="1.094" step="0.01">
            <span class="input-unit">lb/ft</span>
          </div>
        </div>
        <div class="form-group">
          <label>Transverse Wind Speed</label>
          <div class="input-with-unit">
            <input type="number" id="blowWind" value="60">
            <span class="input-unit">mph</span>
          </div>
        </div>
        <div class="form-group">
          <label>Mid-Span Sag at Wind Temp</label>
          <div class="input-with-unit">
            <input type="number" id="blowSag" value="22">
            <span class="input-unit">ft</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Blowout Results</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Blowout Angle (θ)</span>
            <span class="metric-standard">θ = arctan(F_wind / W_vert)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="blowResAngle">--</span>
            <span class="metric-unit">deg</span>
          </div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Horizontal Displacement</span>
            <span class="metric-standard">Displacement = Sag · sin(θ)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="blowResDisp">--</span>
            <span class="metric-unit">ft</span>
          </div>
          <div class="status-badge pass" id="blowRowBadge">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const d = parseFloat(document.getElementById("blowDiam").value) || 1.108;
    const w = parseFloat(document.getElementById("blowWt").value) || 1.094;
    const v = parseFloat(document.getElementById("blowWind").value) || 60;
    const sag = parseFloat(document.getElementById("blowSag").value) || 22;

    const windPressPsf = 0.00256 * Math.pow(v, 2);
    const windForce = windPressPsf * (d / 12.0);
    const angleRad = Math.atan2(windForce, w);
    const angleDeg = angleRad * (180 / Math.PI);
    const disp = sag * Math.sin(angleRad);

    document.getElementById("blowResAngle").textContent = angleDeg.toFixed(1);
    document.getElementById("blowResDisp").textContent = disp.toFixed(2);
    document.getElementById("blowRowBadge").textContent = `Recommended corridor margin: at least ${(disp + 10).toFixed(1)} ft`;
  };

  ["blowDiam", "blowWt", "blowWind", "blowSag"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 4. IEEE 738 Ampacity Tool
function renderAmpacityTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>IEEE Std 738 Thermal Ampacity</h3>
        <p class="panel-desc">Steady-state heat balance for Drake 795 ACSR (d = 28.1 mm)</p>
        <div class="form-group">
          <label>Max Conductor Operating Temp</label>
          <div class="input-with-unit">
            <input type="number" id="ampTc" value="75">
            <span class="input-unit">°C</span>
          </div>
        </div>
        <div class="form-group">
          <label>Ambient Temperature</label>
          <div class="input-with-unit">
            <input type="number" id="ampTa" value="35">
            <span class="input-unit">°C</span>
          </div>
        </div>
        <div class="form-group">
          <label>Perpendicular Wind Speed</label>
          <div class="input-with-unit">
            <input type="number" id="ampWind" value="0.61" step="0.1">
            <span class="input-unit">m/s</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Ampacity Ratings</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Allowable Current</span>
            <span class="metric-standard">IEEE 738 Heat Balance</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="ampResAmps">--</span>
            <span class="metric-unit">Amperes</span>
          </div>
          <div class="status-badge pass" id="ampResMva">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const tc = parseFloat(document.getElementById("ampTc").value) || 75;
    const ta = parseFloat(document.getElementById("ampTa").value) || 35;
    const v = parseFloat(document.getElementById("ampWind").value) || 0.61;
    const dMeters = 0.0281;
    const deltaT = Math.max(1, tc - ta);
    const rPerMeter = 0.072 / 1000.0;

    const reynolds = (dMeters * v * 1.2) / 1.81e-5;
    const qc = (1.01 + 0.371 * Math.pow(reynolds, 0.52)) * 0.0242 * deltaT;
    const qr = Math.PI * dMeters * 0.8 * 5.67e-8 * (Math.pow(tc + 273.15, 4) - Math.pow(ta + 273.15, 4));
    const qs = 0.8 * 1000 * dMeters;

    const net = Math.max(0, qc + qr - qs);
    const amps = Math.sqrt(net / rPerMeter);
    const mva230 = (Math.sqrt(3) * 230 * amps) / 1000;

    document.getElementById("ampResAmps").textContent = Math.round(amps);
    document.getElementById("ampResMva").textContent = `3-Phase 230kV Capacity: ${mva230.toFixed(1)} MVA`;
  };

  ["ampTc", "ampTa", "ampWind"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 5. Corrosion: Zinc Life
function renderZincLifeTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Galvanizing Life Estimator</h3>
        <p class="panel-desc">Hot-dip zinc coating depletion per ISO 9223 / ASTM A123</p>
        <div class="form-group">
          <label>Zinc Coating Thickness</label>
          <div class="input-with-unit">
            <input type="number" id="zincThickness" value="85">
            <span class="input-unit">µm</span>
          </div>
        </div>
        <div class="form-group">
          <label>ISO 9223 Corrosivity Category</label>
          <select id="zincCategory">
            <option value="0.1">C1 - Very Low (0.1 µm/yr)</option>
            <option value="0.5">C2 - Low (0.5 µm/yr)</option>
            <option value="1.5" selected>C3 - Medium (1.5 µm/yr)</option>
            <option value="3.0">C4 - High (3.0 µm/yr)</option>
            <option value="6.0">C5 - Very High (6.0 µm/yr)</option>
            <option value="15.0">CX - Extreme Offshore (15.0 µm/yr)</option>
          </select>
        </div>
      </div>
      <div class="panel">
        <h3>Lifespan Projection</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Years to Steel Exposure</span>
            <span class="metric-standard">ISO 9223 Model</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="zincResYears">--</span>
            <span class="metric-unit">Years</span>
          </div>
          <div class="status-badge pass" id="zincResRec">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const thick = parseFloat(document.getElementById("zincThickness").value) || 85;
    const rate = parseFloat(document.getElementById("zincCategory").value) || 1.5;
    const years = thick / rate;

    document.getElementById("zincResYears").textContent = years.toFixed(1);
    const badge = document.getElementById("zincResRec");
    if (years > 30) {
      badge.className = "status-badge pass";
      badge.textContent = "Long-term coating integrity verified. Standard 10-year patrol.";
    } else if (years > 15) {
      badge.className = "status-badge warn";
      badge.textContent = "Moderate lifespan. Schedule dry film thickness (DFT) check at year 15.";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "Rapid depletion. Barrier paint overcoat or metallizing required.";
    }
  };

  document.getElementById("zincThickness").addEventListener("input", calc);
  document.getElementById("zincCategory").addEventListener("change", calc);
  calc();
}

// 6. Soil Anchor Loss
function renderSoilAnchorTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Soil Anchor Loss (Romanoff)</h3>
        <p class="panel-desc">Underground pitting model for buried guy anchor rods & grillages</p>
        <div class="form-group">
          <label>Anchor Rod Initial Diameter</label>
          <div class="input-with-unit">
            <input type="number" id="anchorDiam" value="1.0" step="0.1">
            <span class="input-unit">in</span>
          </div>
        </div>
        <div class="form-group">
          <label>Soil Resistivity</label>
          <div class="input-with-unit">
            <input type="number" id="anchorRes" value="35">
            <span class="input-unit">Ω·m</span>
          </div>
        </div>
        <div class="form-group">
          <label>Years in Service</label>
          <div class="input-with-unit">
            <input type="number" id="anchorYears" value="40">
            <span class="input-unit">years</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Degradation Analysis</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Remaining Diameter</span>
            <span class="metric-standard">P = k·t^0.55</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="anchorResRemDiam">--</span>
            <span class="metric-unit">in</span>
          </div>
          <div class="status-badge pass" id="anchorResAreaPct">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const diam = parseFloat(document.getElementById("anchorDiam").value) || 1.0;
    const res = parseFloat(document.getElementById("anchorRes").value) || 35;
    const yrs = parseFloat(document.getElementById("anchorYears").value) || 40;

    const baseK = res < 15 ? 8.0 : res < 30 ? 5.5 : res < 60 ? 3.5 : 1.5;
    const penMils = baseK * Math.pow(Math.max(0, yrs - 15), 0.55) * 12.0;
    const penIn = penMils / 1000.0;
    const rRem = Math.max(0.05, (diam / 2.0) - penIn);
    const remDiam = rRem * 2.0;
    const areaPct = Math.pow(remDiam / diam, 2) * 100;

    document.getElementById("anchorResRemDiam").textContent = remDiam.toFixed(2);
    const badge = document.getElementById("anchorResAreaPct");
    badge.textContent = `Remaining Steel Area: ${areaPct.toFixed(1)}%`;
    badge.className = areaPct > 65 ? "status-badge pass" : "status-badge alert";
  };

  ["anchorDiam", "anchorRes", "anchorYears"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 7. AC Corrosion Tool
function renderAcCorrosionTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>AC Interference & Corrosion Risk</h3>
        <p class="panel-desc">AC discharge current density per ISO 18086 / NACE SP0106</p>
        <div class="form-group">
          <label>Induced AC Voltage</label>
          <div class="input-with-unit">
            <input type="number" id="acVolt" value="18">
            <span class="input-unit">V_ac</span>
          </div>
        </div>
        <div class="form-group">
          <label>Soil Resistivity</label>
          <div class="input-with-unit">
            <input type="number" id="acSoil" value="40">
            <span class="input-unit">Ω·m</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Risk Classification</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>AC Current Density (J_ac)</span>
            <span class="metric-standard">J_ac = (8·V)/(π·ρ·d)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="acResJac">--</span>
            <span class="metric-unit">A/m²</span>
          </div>
          <div class="status-badge warn" id="acResBadge">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const v = parseFloat(document.getElementById("acVolt").value) || 18;
    const rho = parseFloat(document.getElementById("acSoil").value) || 40;
    const d = 0.01128; // 1 cm2 holiday
    const jAc = (8 * v) / (Math.PI * rho * d);

    document.getElementById("acResJac").textContent = jAc.toFixed(1);
    const badge = document.getElementById("acResBadge");
    if (jAc < 30) {
      badge.className = "status-badge pass";
      badge.textContent = "Low Risk (<30 A/m²): Standard cathodic protection adequate.";
    } else if (jAc <= 100) {
      badge.className = "status-badge warn";
      badge.textContent = "Medium Risk (30-100 A/m²): AC mitigation recommended.";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "Severe Risk (>100 A/m²): Solid-state decoupler & zinc ribbon required.";
    }
  };

  ["acVolt", "acSoil"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 8. CP Anode Tool
function renderCpAnodeTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Cathodic Protection Anode Sizing</h3>
        <p class="panel-desc">Sacrificial magnesium or zinc anode sizing for tower foundations</p>
        <div class="form-group">
          <label>Buried Steel Surface Area</label>
          <div class="input-with-unit">
            <input type="number" id="cpArea" value="150">
            <span class="input-unit">sq ft</span>
          </div>
        </div>
        <div class="form-group">
          <label>Current Density Requirement</label>
          <div class="input-with-unit">
            <input type="number" id="cpDensity" value="2.0" step="0.5">
            <span class="input-unit">mA/sq ft</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Required Anodes</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Recommended Magnesium Anodes (30yr)</span>
            <span class="metric-standard">NACE SP0169</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="cpResCount">--</span>
            <span class="metric-unit">Anodes</span>
          </div>
          <div class="status-badge pass" id="cpResMass">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const area = parseFloat(document.getElementById("cpArea").value) || 150;
    const cd = parseFloat(document.getElementById("cpDensity").value) || 2.0;
    const totalCurrent = (area * cd) / 1000.0;
    const ampHours = totalCurrent * 8760 * 30; // 30 years
    const totalKg = ampHours / 550; // Magnesium ~550 A-h/kg net
    const count = Math.ceil(totalKg / 7.7);

    document.getElementById("cpResCount").textContent = count;
    document.getElementById("cpResMass").textContent = `Total Mass: ${totalKg.toFixed(1)} kg (${(totalKg * 2.2).toFixed(0)} lbs) H-1 Magnesium`;
  };

  ["cpArea", "cpDensity"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 9. Defect Matrix Tool
function renderDefectMatrixTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Transmission Defect Scoring (DSI)</h3>
        <p class="panel-desc">Multi-defect evaluation with priority action categorization</p>
        <div class="form-group">
          <label>Foundation Defect Level (0=Sound, 3=Severe Uplift/Crack)</label>
          <select id="defFound">
            <option value="0">Level 0: Sound / Normal</option>
            <option value="1">Level 1: Minor hairline cracking</option>
            <option value="2">Level 2: Concrete spalling / exposed rebar</option>
            <option value="3">Level 3: Severe displacement / anchor necking</option>
          </select>
        </div>
        <div class="form-group">
          <label>Structural Member Bent or Buckled?</label>
          <select id="defBent">
            <option value="0">No: All members straight</option>
            <option value="1">Yes: Angle leg or diagonal bent</option>
          </select>
        </div>
        <div class="form-group">
          <label>Steel Rust Grade (0=Zinc, 4=Flaking Rust)</label>
          <select id="defRust">
            <option value="0">Grade 0: Bright Zinc</option>
            <option value="1">Grade 1: Dull gray patina</option>
            <option value="2">Grade 2: Spot pinpoint rust < 5%</option>
            <option value="3">Grade 3: Generalized rust</option>
            <option value="4">Grade 4: Flaking laminar rust</option>
          </select>
        </div>
      </div>
      <div class="panel">
        <h3>Urgency & Action Tier</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Action Priority Tier</span>
            <span class="metric-standard">EPRI Guideline</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="defResTier">--</span>
          </div>
          <div class="status-badge alert" id="defResBadge">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const f = parseInt(document.getElementById("defFound").value);
    const b = parseInt(document.getElementById("defBent").value);
    const r = parseInt(document.getElementById("defRust").value);
    let score = f * 18 + b * 25 + r * 12;

    const tierElem = document.getElementById("defResTier");
    const badgeElem = document.getElementById("defResBadge");

    if (score >= 45) {
      tierElem.textContent = "P1: Emergency";
      badgeElem.className = "status-badge alert";
      badgeElem.textContent = "Immediate remediation required (<24-48 hrs). NESC Rule 260 violation.";
    } else if (score >= 25) {
      tierElem.textContent = "P2: High Priority";
      badgeElem.className = "status-badge warn";
      badgeElem.textContent = "Schedule repair in <30 days maintenance cycle.";
    } else {
      tierElem.textContent = "P4: Low / Monitored";
      badgeElem.className = "status-badge pass";
      badgeElem.textContent = "Acceptable. Continue routine patrol schedule.";
    }
  };

  ["defFound", "defBent", "defRust"].forEach(id => {
    document.getElementById(id).addEventListener("change", calc);
  });
  calc();
}

// 10. Wood Pole Tool
function renderWoodPoleTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Wood Pole Shell Soundness</h3>
        <p class="panel-desc">ASCE Manual 91 / ANSI O5.1 hollow cylinder capacity</p>
        <div class="form-group">
          <label>Original Circumference</label>
          <div class="input-with-unit">
            <input type="number" id="poleCirc" value="42">
            <span class="input-unit">in</span>
          </div>
        </div>
        <div class="form-group">
          <label>Measured Sound Wood Shell Thickness</label>
          <div class="input-with-unit">
            <input type="number" id="poleShell" value="3.5" step="0.5">
            <span class="input-unit">in</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Remaining Bending Capacity</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Residual Capacity</span>
            <span class="metric-standard">NESC Rule 261 (67% Limit)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="poleResPct">--</span>
            <span class="metric-unit">%</span>
          </div>
          <div class="status-badge pass" id="poleResBadge">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const circ = parseFloat(document.getElementById("poleCirc").value) || 42;
    const shell = parseFloat(document.getElementById("poleShell").value) || 3.5;
    const outerD = circ / Math.PI;
    const innerD = Math.max(0, outerD - 2 * shell);

    const zOrig = (Math.PI * Math.pow(outerD, 3)) / 32.0;
    const zRem = (Math.PI * (Math.pow(outerD, 4) - Math.pow(innerD, 4))) / (32.0 * outerD);
    const pct = (zRem / zOrig) * 100;

    document.getElementById("poleResPct").textContent = pct.toFixed(1);
    const badge = document.getElementById("poleResBadge");
    if (pct >= 75) {
      badge.className = "status-badge pass";
      badge.textContent = "PASS: Exceeds standard structural requirements.";
    } else if (pct >= 60) {
      badge.className = "status-badge warn";
      badge.textContent = "REINFORCE: Candidate for C-Truss splinting.";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "REJECT / RED TAG: Immediate replacement required.";
    }
  };

  ["poleCirc", "poleShell"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 11. Lattice Buckling Tool
function renderLatticeBucklingTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Lattice Member Buckling (ASCE 10)</h3>
        <p class="panel-desc">Column compression slenderness ratio KL/r and capacity</p>
        <div class="form-group">
          <label>Unbraced Length (L)</label>
          <div class="input-with-unit">
            <input type="number" id="latL" value="60">
            <span class="input-unit">in</span>
          </div>
        </div>
        <div class="form-group">
          <label>Radius of Gyration (r_z min)</label>
          <div class="input-with-unit">
            <input type="number" id="latR" value="0.59" step="0.01">
            <span class="input-unit">in</span>
          </div>
        </div>
        <div class="form-group">
          <label>Cross-Section Area</label>
          <div class="input-with-unit">
            <input type="number" id="latArea" value="1.44" step="0.01">
            <span class="input-unit">sq in</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>ASCE 10 Capacity</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Slenderness Ratio (KL/r)</span>
            <span class="metric-standard">ASCE 10 (Limit <= 200)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="latResKlR">--</span>
          </div>
          <div class="status-badge pass" id="latKlRBadge">--</div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Allowable Axial Load</span>
            <span class="metric-standard">P = F_cr · Area</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="latResLoad">--</span>
            <span class="metric-unit">kips</span>
          </div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const l = parseFloat(document.getElementById("latL").value) || 60;
    const r = parseFloat(document.getElementById("latR").value) || 0.59;
    const area = parseFloat(document.getElementById("latArea").value) || 1.44;

    const klr = l / r;
    const cc = Math.sqrt((2 * Math.PI * Math.PI * 29000) / 36);
    let fcr = 0;
    if (klr <= cc) {
      fcr = (1 - (Math.pow(klr, 2) / (2 * Math.pow(cc, 2)))) * 36;
    } else {
      fcr = (Math.PI * Math.PI * 29000) / Math.pow(klr, 2);
    }
    const load = fcr * area;

    document.getElementById("latResKlR").textContent = klr.toFixed(1);
    document.getElementById("latResLoad").textContent = load.toFixed(1);
    const badge = document.getElementById("latKlRBadge");
    if (klr <= 200) {
      badge.className = "status-badge pass";
      badge.textContent = "Passes ASCE 10 slenderness limit (<= 200)";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "FAIL: Exceeds ASCE 10 maximum compression limit (> 200)";
    }
  };

  ["latL", "latR", "latArea"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 12. Footing Grounding Tool
function renderFootingTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Tower Footing Grounding</h3>
        <p class="panel-desc">IEEE Std 80 / 142 impedance with soil ionization</p>
        <div class="form-group">
          <label>Soil Resistivity</label>
          <div class="input-with-unit">
            <input type="number" id="ftSoil" value="250">
            <span class="input-unit">Ω·m</span>
          </div>
        </div>
        <div class="form-group">
          <label>Number of Driven Ground Rods</label>
          <input type="number" id="ftRods" value="4">
        </div>
        <div class="form-group">
          <label>Counterpoise Trench Wire</label>
          <div class="input-with-unit">
            <input type="number" id="ftCp" value="30">
            <span class="input-unit">m</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Impedance Results</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>60Hz Power Frequency Resistance</span>
            <span class="metric-standard">Target < 10-15 Ω</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="ftResLowFreq">--</span>
            <span class="metric-unit">Ω</span>
          </div>
          <div class="status-badge pass" id="ftBadge">--</div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Surge Impulse Resistance (R_i)</span>
            <span class="metric-standard">40 kA Stroke Ionization</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="ftResImpulse">--</span>
            <span class="metric-unit">Ω</span>
          </div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const rho = parseFloat(document.getElementById("ftSoil").value) || 250;
    const rods = parseInt(document.getElementById("ftRods").value) || 4;
    const cp = parseFloat(document.getElementById("ftCp").value) || 30;

    const singleRodR = (rho / (2 * Math.PI * 3.05)) * (Math.log(4 * 3.05 / 0.008) - 1);
    const rodsR = singleRodR / (rods * 0.8);
    const cpR = (rho / (Math.PI * cp)) * (Math.log(2 * cp / Math.sqrt(2 * 0.8 * 0.005)) - 1);
    const rLow = Math.max(0.5, 1 / ((1 / rodsR) + (1 / cpR)));

    const ig = (rho * 400) / (2 * Math.PI * Math.pow(rLow, 2));
    const rImpulse = rLow / Math.sqrt(1 + (40 / Math.max(1, ig)));

    document.getElementById("ftResLowFreq").textContent = rLow.toFixed(1);
    document.getElementById("ftResImpulse").textContent = rImpulse.toFixed(1);

    const badge = document.getElementById("ftBadge");
    if (rLow <= 10) {
      badge.className = "status-badge pass";
      badge.textContent = "Optimal (< 10 Ω). Excellent lightning withstand.";
    } else if (rLow <= 20) {
      badge.className = "status-badge warn";
      badge.textContent = "Marginal (10-20 Ω). Consider extra counterpoise.";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "Elevated (> 20 Ω). High backflashover risk.";
    }
  };

  ["ftSoil", "ftRods", "ftCp"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 13. Shielding Angle Tool
function renderShieldingTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Shielding Angle Analysis</h3>
        <p class="panel-desc">Electrogeometric Model (EGM) protection cone for OHGW/OPGW</p>
        <div class="form-group">
          <label>Shield Wire Height (H_s)</label>
          <div class="input-with-unit">
            <input type="number" id="shH" value="35">
            <span class="input-unit">m</span>
          </div>
        </div>
        <div class="form-group">
          <label>Phase Conductor Height (H_p)</label>
          <div class="input-with-unit">
            <input type="number" id="shP" value="26">
            <span class="input-unit">m</span>
          </div>
        </div>
        <div class="form-group">
          <label>Horizontal Separation (X_s)</label>
          <div class="input-with-unit">
            <input type="number" id="shSep" value="4.5">
            <span class="input-unit">m</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Shielding Evaluation</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Shield Angle (θ)</span>
            <span class="metric-standard">Recommended <= 30°</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="shResAngle">--</span>
            <span class="metric-unit">degrees</span>
          </div>
          <div class="status-badge pass" id="shResBadge">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const hs = parseFloat(document.getElementById("shH").value) || 35;
    const hp = parseFloat(document.getElementById("shP").value) || 26;
    const xs = parseFloat(document.getElementById("shSep").value) || 4.5;
    const deltaH = Math.max(0.5, hs - hp);
    const thetaDeg = Math.atan2(xs, deltaH) * (180 / Math.PI);

    document.getElementById("shResAngle").textContent = thetaDeg.toFixed(1);
    const badge = document.getElementById("shResBadge");
    if (thetaDeg <= 30) {
      badge.className = "status-badge pass";
      badge.textContent = "Effective: Shield angle is within recommended <= 30° envelope.";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "Inadequate: Shield angle exceeds 30°. Vulnerable to direct flashover.";
    }
  };

  ["shH", "shP", "shSep"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 14. Backflashover Tool
function renderBackflashoverTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Critical Backflashover Current</h3>
        <p class="panel-desc">Insulator crossarm CFO and outage rate per IEEE Std 1243</p>
        <div class="form-group">
          <label>Insulator String CFO</label>
          <div class="input-with-unit">
            <input type="number" id="bfoCfo" value="1200">
            <span class="input-unit">kV</span>
          </div>
        </div>
        <div class="form-group">
          <label>Tower Footing Resistance</label>
          <div class="input-with-unit">
            <input type="number" id="bfoR" value="15">
            <span class="input-unit">Ω</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Outage Rate Projection</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Critical Current (I_c)</span>
            <span class="metric-standard">I_c = CFO / ((1-K)·R + Zt/6)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="bfoResIc">--</span>
            <span class="metric-unit">kA</span>
          </div>
          <div class="status-badge pass" id="bfoResBfor">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const cfo = parseFloat(document.getElementById("bfoCfo").value) || 1200;
    const r = parseFloat(document.getElementById("bfoR").value) || 15;
    const ic = cfo / ((1 - 0.32) * r + 25);
    const prob = 1.0 / (1.0 + Math.pow(ic / 31.0, 2.6));
    const bfor = 4.0 * 100 * 0.08 * prob;

    document.getElementById("bfoResIc").textContent = ic.toFixed(1);
    const badge = document.getElementById("bfoResBfor");
    badge.textContent = `Predicted Outage Rate: ${bfor.toFixed(2)} trips / 100 km-yr`;
    badge.className = bfor < 1.0 ? "status-badge pass" : "status-badge warn";
  };

  ["bfoCfo", "bfoR"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 15. Wenner Test Tool
function renderWennerTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Wenner 4-Point Soil Resistivity</h3>
        <p class="panel-desc">IEEE Std 81 apparent soil resistivity measurement</p>
        <div class="form-group">
          <label>Pin Spacing (a)</label>
          <div class="input-with-unit">
            <input type="number" id="wennerA" value="5">
            <span class="input-unit">m</span>
          </div>
        </div>
        <div class="form-group">
          <label>Measured Resistance (R)</label>
          <div class="input-with-unit">
            <input type="number" id="wennerR" value="4.2" step="0.1">
            <span class="input-unit">Ω</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Apparent Resistivity</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Apparent Resistivity (ρ)</span>
            <span class="metric-standard">ρ = 2·π·a·R</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="wennerResRho">--</span>
            <span class="metric-unit">Ω·m</span>
          </div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const a = parseFloat(document.getElementById("wennerA").value) || 5;
    const r = parseFloat(document.getElementById("wennerR").value) || 4.2;
    const rho = 2 * Math.PI * a * r;
    document.getElementById("wennerResRho").textContent = rho.toFixed(1);
  };

  ["wennerA", "wennerR"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 16. Conductor Database Tool
function renderConductorDbTool(container) {
  container.innerHTML = `
    <div class="panel" style="margin-bottom: 20px;">
      <h3>Standard Conductor Database</h3>
      <p class="panel-desc">ASTM B232 / B856 specifications for overhead conductors</p>
      <div class="form-group">
        <label>Select Conductor</label>
        <select id="condSelector">
          ${CONDUCTORS.map((c, i) => `<option value="${i}" ${c.codeWord === "Drake" ? "selected" : ""}>${c.codeWord} (${c.type} ${c.kcmil} kcmil)</option>`).join('')}
        </select>
      </div>
    </div>
    <div id="conductorDetailCard"></div>
  `;

  const select = document.getElementById("condSelector");
  const renderDetail = () => {
    const c = CONDUCTORS[parseInt(select.value)];
    document.getElementById("conductorDetailCard").innerHTML = `
      <div class="panel">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 14px;">
          <div>
            <h2 style="color: var(--primary-amber); font-size: 1.5rem;">${c.codeWord} (${c.type})</h2>
            <p style="color: var(--text-muted); font-size: 0.9rem;">${c.kcmil} kcmil • Stranding ${c.stranding}</p>
          </div>
          <div class="status-badge pass" style="font-size: 1rem;">${c.amp} Amperes</div>
        </div>
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 14px;">
          <div class="metric-box">
            <div class="metric-header"><span>Overall Diameter</span></div>
            <div class="metric-value-row">
              <span class="metric-value" style="font-size: 1.3rem;">${c.diamIn}"</span>
              <span class="metric-unit">(${(c.diamIn * 25.4).toFixed(1)} mm)</span>
            </div>
          </div>
          <div class="metric-box">
            <div class="metric-header"><span>Linear Weight</span></div>
            <div class="metric-value-row">
              <span class="metric-value" style="font-size: 1.3rem;">${c.wtLbFt}</span>
              <span class="metric-unit">lb/ft (${(c.wtLbFt * 1.488).toFixed(2)} kg/m)</span>
            </div>
          </div>
          <div class="metric-box">
            <div class="metric-header"><span>Rated Breaking Strength</span></div>
            <div class="metric-value-row">
              <span class="metric-value" style="font-size: 1.3rem;">${c.rbsLbf}</span>
              <span class="metric-unit">lbf (${(c.rbsLbf * 0.004448).toFixed(1)} kN)</span>
            </div>
          </div>
          <div class="metric-box">
            <div class="metric-header"><span>AC Resistance @ 75°C</span></div>
            <div class="metric-value-row">
              <span class="metric-value" style="font-size: 1.3rem;">${c.r75}</span>
              <span class="metric-unit">Ω / mile</span>
            </div>
          </div>
        </div>
      </div>
    `;
  };

  select.addEventListener("change", renderDetail);
  renderDetail();
}

// 17. Aeolian Vibration Tool
function renderAeolianTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Aeolian Vibration Damper Positioning</h3>
        <p class="panel-desc">Stockbridge damper distance x = 0.8·(λ/2) from suspension clamp</p>
        <div class="form-group">
          <label>Conductor Diameter</label>
          <div class="input-with-unit">
            <input type="number" id="aeoDiam" value="28.14" step="0.1">
            <span class="input-unit">mm</span>
          </div>
        </div>
        <div class="form-group">
          <label>Everyday Tension (T_0)</label>
          <div class="input-with-unit">
            <input type="number" id="aeoTension" value="28.0" step="0.5">
            <span class="input-unit">kN</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Damper Position</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Damper Distance from Clamp</span>
            <span class="metric-standard">IEEE Std 664 / CIGRE</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="aeoResDist">--</span>
            <span class="metric-unit">inches</span>
          </div>
          <div class="status-badge pass" id="aeoResFreq">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const dMm = parseFloat(document.getElementById("aeoDiam").value) || 28.14;
    const tKn = parseFloat(document.getElementById("aeoTension").value) || 28.0;

    const dM = dMm / 1000.0;
    const freq = (0.185 * 3.5) / dM;
    const waveVel = Math.sqrt((tKn * 1000) / 1.628);
    const halfLoop = (waveVel / freq) / 2.0;
    const damperM = 0.8 * halfLoop;
    const damperIn = damperM * 39.37;

    document.getElementById("aeoResDist").textContent = damperIn.toFixed(1);
    document.getElementById("aeoResFreq").textContent = `Strouhal Frequency: ${freq.toFixed(1)} Hz (${damperM.toFixed(2)} m)`;
  };

  ["aeoDiam", "aeoTension"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 18. Insulator Creepage Tool
function renderInsulatorTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Insulator Creepage & Rating</h3>
        <p class="panel-desc">IEC 60815 pollution severity creepage and ANSI C29.2 safety factor</p>
        <div class="form-group">
          <label>System Operating Voltage</label>
          <div class="input-with-unit">
            <input type="number" id="insVolt" value="230">
            <span class="input-unit">kV</span>
          </div>
        </div>
        <div class="form-group">
          <label>Pollution Severity (IEC 60815)</label>
          <select id="insPoll">
            <option value="16">Light (16 mm/kV)</option>
            <option value="20">Medium (20 mm/kV)</option>
            <option value="25" selected>Heavy (25 mm/kV)</option>
            <option value="31">Very Heavy (31 mm/kV)</option>
          </select>
        </div>
      </div>
      <div class="panel">
        <h3>String Sizing</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Required Standard 10" Discs</span>
            <span class="metric-standard">IEC 60815</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="insResDiscs">--</span>
            <span class="metric-unit">Bells</span>
          </div>
          <div class="status-badge pass" id="insResCreepage">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const v = parseFloat(document.getElementById("insVolt").value) || 230;
    const poll = parseFloat(document.getElementById("insPoll").value) || 25;
    const requiredTotalMm = poll * (v * 1.05);
    const discs = Math.ceil(requiredTotalMm / 292.0);

    document.getElementById("insResDiscs").textContent = discs;
    document.getElementById("insResCreepage").textContent = `Total Creepage: ${Math.round(requiredTotalMm)} mm (${((discs * 146) / 1000).toFixed(2)} m string length)`;
  };

  ["insVolt", "insPoll"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 19. EMF Profile Tool
function renderEmfTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>EMF Ground-Level Profiler</h3>
        <p class="panel-desc">IEEE Std 644 lateral field calculation against ICNIRP public limits</p>
        <div class="form-group">
          <label>Line Operating Voltage</label>
          <div class="input-with-unit">
            <input type="number" id="emfVolt" value="230">
            <span class="input-unit">kV</span>
          </div>
        </div>
        <div class="form-group">
          <label>Balanced Line Current</label>
          <div class="input-with-unit">
            <input type="number" id="emfCurr" value="800">
            <span class="input-unit">Amperes</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Field Levels</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Peak Electric Field (E)</span>
            <span class="metric-standard">ICNIRP Limit 5.0 kV/m</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="emfResE">--</span>
            <span class="metric-unit">kV / m</span>
          </div>
          <div class="status-badge pass" id="emfBadgeE">Passes ICNIRP public threshold</div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Peak Magnetic Field (B)</span>
            <span class="metric-standard">ICNIRP Limit 2000 mG</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="emfResB">--</span>
            <span class="metric-unit">mG</span>
          </div>
          <div class="status-badge pass" id="emfBadgeB">Well within international limits</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const v = parseFloat(document.getElementById("emfVolt").value) || 230;
    const i = parseFloat(document.getElementById("emfCurr").value) || 800;

    const ePeak = (v / 230.0) * 1.85;
    const bPeak = (i / 800.0) * 42.5;

    document.getElementById("emfResE").textContent = ePeak.toFixed(2);
    document.getElementById("emfResB").textContent = bPeak.toFixed(1);
  };

  ["emfVolt", "emfCurr"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 20. ROW Width Tool
function renderRowWidthTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Right-of-Way Minimum Width</h3>
        <p class="panel-desc">NESC Rule 234 Table 234-1 corridor sizing with conductor blowout</p>
        <div class="form-group">
          <label>Structure Footprint Width</label>
          <div class="input-with-unit">
            <input type="number" id="rowBase" value="25">
            <span class="input-unit">ft</span>
          </div>
        </div>
        <div class="form-group">
          <label>Mid-Span Conductor Sag</label>
          <div class="input-with-unit">
            <input type="number" id="rowSag" value="22">
            <span class="input-unit">ft</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Required ROW Corridor</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Total Recommended Corridor Width</span>
            <span class="metric-standard">NESC Rule 234</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="rowResTotal">--</span>
            <span class="metric-unit">ft</span>
          </div>
          <div class="status-badge pass" id="rowResBreakdown">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const base = parseFloat(document.getElementById("rowBase").value) || 25;
    const sag = parseFloat(document.getElementById("rowSag").value) || 22;
    const blowout = sag * Math.sin(25 * (Math.PI / 180));
    const elecClearance = 7.5 + ((230 - 22) * 0.4 / 12.0);
    const dangerTree = 10.0;
    const total = base + 2 * (blowout + elecClearance + dangerTree);

    document.getElementById("rowResTotal").textContent = Math.ceil(total);
    document.getElementById("rowResBreakdown").textContent = `Base ${base}ft + 2×(Blowout ${blowout.toFixed(1)}ft + Clear ${elecClearance.toFixed(1)}ft + Buffer 10ft)`;
  };

  ["rowBase", "rowSag"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 21. Corona Noise Tool
function renderCoronaTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Corona Loss & Rain Audible Noise</h3>
        <p class="panel-desc">EPRI Transmission Line Reference Book formulation</p>
        <div class="form-group">
          <label>System Operating Voltage</label>
          <div class="input-with-unit">
            <input type="number" id="corVolt" value="500">
            <span class="input-unit">kV</span>
          </div>
        </div>
        <div class="form-group">
          <label>Subconductors in Phase Bundle</label>
          <input type="number" id="corBundle" value="3">
        </div>
      </div>
      <div class="panel">
        <h3>Environmental Acoustics</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Wet Rain Audible Noise</span>
            <span class="metric-standard">Target <= 53-58 dBA</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="corResDba">--</span>
            <span class="metric-unit">dBA @ ROW</span>
          </div>
          <div class="status-badge pass" id="corResBadge">Complies with EPRI residential threshold</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const v = parseFloat(document.getElementById("corVolt").value) || 500;
    const b = parseInt(document.getElementById("corBundle").value) || 3;
    const grad = ((v / Math.sqrt(3)) / (1.4 * Math.log(1000 / 1.4))) * 0.75;
    const wetNoise = 52.0 + 12.0 * Math.log10(b) + 2.5 * (grad - 16.0);

    document.getElementById("corResDba").textContent = wetNoise.toFixed(1);
  };

  ["corVolt", "corBundle"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// ======================== GLOSSARY & SEARCH ========================

function renderGlossaryView(container) {
  container.innerHTML = `
    <div class="category-hero">
      <div class="hero-info">
        <h2>Transmission Engineering Glossary</h2>
        <p>Key terms, mathematical formulas, and standards references (IEEE, NESC, ASCE, CIGRE, ISO)</p>
      </div>
    </div>
    <div class="glossary-grid">
      ${GLOSSARY_TERMS.map(t => `
        <div class="glossary-card">
          <div class="glossary-header">
            <span class="glossary-title">${t.term}</span>
            <span class="glossary-std">${t.std}</span>
          </div>
          <p class="glossary-def">${t.def}</p>
          <p class="glossary-context"><strong>Engineering Context:</strong> ${t.ctx}</p>
        </div>
      `).join('')}
    </div>
  `;
}

function renderSearchResults(query) {
  const container = document.getElementById("contentArea");
  const filteredTerms = GLOSSARY_TERMS.filter(t => 
    t.term.toLowerCase().includes(query) ||
    t.def.toLowerCase().includes(query) ||
    t.std.toLowerCase().includes(query)
  );

  container.innerHTML = `
    <div class="category-hero">
      <div class="hero-info">
        <h2>Search Results for "${query}"</h2>
        <p>Found ${filteredTerms.length} matching terms and tools</p>
      </div>
    </div>
    <div class="glossary-grid">
      ${filteredTerms.map(t => `
        <div class="glossary-card">
          <div class="glossary-header">
            <span class="glossary-title">${t.term}</span>
            <span class="glossary-std">${t.std}</span>
          </div>
          <p class="glossary-def">${t.def}</p>
          <p class="glossary-context"><strong>Category:</strong> ${t.cat} | <strong>Context:</strong> ${t.ctx}</p>
        </div>
      `).join('')}
    </div>
  `;
}

window.setVal = function(id, val) {
  const el = document.getElementById(id);
  if (el) {
    el.value = val;
    el.dispatchEvent(new Event("input"));
  }
};
