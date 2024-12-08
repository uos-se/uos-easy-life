const fs = require("fs").promises;
const crypto = require("crypto");

async function getCourseId(course) {
  const subjectNumber = course["SBJC_NO"];
  const department = course["OGDP_SCSBJT_NM"];
  const point = course["CMPN_PNT"];
  const categort1 = course["COURSE_DIVCD_NM"];
  const categort2 = course["COURSE_DIVCD_NM2"];

  const idString = [
    subjectNumber,
    department,
    point,
    categort1,
    categort2,
  ].join("#");

  const hash = crypto.createHash("sha256");
  hash.update(idString);
  return hash.digest("hex");
}

async function main() {
  const uniqueCourseIDs = new Map();
  const list = [];
  const files = await fs.readdir("./data");
  for (const file of files) {
    if (file.endsWith(".json")) {
      const year = file.split("-")[0];
      const data = await fs.readFile("./data/" + file, "utf-8");
      for (const item of JSON.parse(data)["dsMain"]) {
        const id = await getCourseId(item);
        item["_id"] = id;
        item["LAST_OPEN_YEAR"] = year;
        if (uniqueCourseIDs.has(id)) {
          const existingItem = uniqueCourseIDs.get(id);
          // 마지막으로 개설된 연도가 더 최근인 경우에만 업데이트
          if (existingItem["LAST_OPEN_YEAR"] < year) {
            uniqueCourseIDs.set(id, item);
          }
          continue;
        }
        uniqueCourseIDs.set(id, item);
      }
    }
  }
  for (const [, value] of uniqueCourseIDs) {
    list.push(value);
  }

  console.log(`Merged ${list.length} courses`);
  await fs.writeFile("merged.json", JSON.stringify(list, null, 2));
}

main();
