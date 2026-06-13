const ADS = [
  "https://tendenciascreativas.com.ar/wp-content/uploads/2018/03/PubliSedal.jpg",
  "https://cloudfront-eu-central-1.images.arcpublishing.com/prisaradiolos40/MRMNVRTFHNMZXLEQ3U674TLFAA.jpg",
  "https://static.boredpanda.com/blog/wp-content/uploads/2026/01/funny-print-ads-cover_675.jpg",
  "https://s-media-cache-ak0.pinimg.com/originals/d1/02/5d/d1025d47f4391f07ff76f949635b6f2c.jpg"
];

let current_ad = 0;

export const getNextAd = () => {
    const ad = ADS[current_ad];
    current_ad = (current_ad + 1) % ADS.length;

    return ad;
}