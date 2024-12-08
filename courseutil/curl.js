const fs = require("fs/promises");

const cookie = "";

async function get(year, semester) {
  console.log(year, semester);
  const query = {
    _AUTH_MENU_KEY: "SucrMjTimeInq_5",
    _AUTH_PGM_ID: "SucrMjTimeInq",
    __PRVC_PSBLTY_YN: "N",
    _AUTH_TASK_AUTHRT_ID: "CCMN_SVC",
    "default.locale": "CCMN101.KOR",
    "@d1#strAcyr": year + "",
    "@d1#strSemstrCd": "CCMN031." + semester,
    "@d1#strCkbYn": "",
    "@d1#strCkbYn2": "",
    "@d1#strUnivCd": "",
    "@d1#strOgdpScsbjtCd": "",
    "@d1#strSearchDiv": "",
    "@d1#strCmpnDivcd": "",
    "@d1#strSbjcNo": "",
    "@d1#strSbjcNm": "",
    "@d1#strDvclNo": "",
    "@d1#strEtcYn": "",
    "@d1#strCnctrLesnYn": "",
    "@d1#strPeriod": "",
    "@d1#strDay": "",
    "@d1#strCourseDivcd": "",
    "@d1#strUnivGdhlDeptCd": "200",
    "@d#": "@d1#",
    "@d1#": "dmReqKey",
    "@d1#tp": "dm",
  };
  const body = new URLSearchParams(query).toString();

  const res = await fetch("https://wise.uos.ac.kr/SCH/SucrMjTimeInq/list.do", {
    headers: {
      accept: "*/*",
      "accept-language": "en-US,en;q=0.9,ko-KR;q=0.8,ko;q=0.7",
      "content-type": "application/x-www-form-urlencoded; charset=UTF-8",
      "sec-ch-ua":
        '"Google Chrome";v="131", "Chromium";v="131", "Not_A Brand";v="24"',
      "sec-ch-ua-mobile": "?0",
      "sec-ch-ua-platform": '"macOS"',
      "sec-fetch-dest": "empty",
      "sec-fetch-mode": "cors",
      "sec-fetch-site": "same-origin",
      "x-requested-with": "XMLHttpRequest",
      cookie: cookie,
      Referer: "https://wise.uos.ac.kr/index.do",
      "Referrer-Policy": "strict-origin-when-cross-origin",
    },
    body: body,
    method: "POST",
  });

  const filename = `./data/${year}-${semester}.json`;
  const data = await res.json();
  await fs.writeFile(filename, JSON.stringify(data));
}

async function main() {
  const years = [];
  for (let year = 2010; year <= new Date().getFullYear(); year++) {
    years.push(year + "");
  }
  const semesters = ["10", "20", "30", "40", "11", "21"];
  for (const year of years) {
    for (const semester of semesters) {
      await get(year, semester);
    }
  }
}

main();
