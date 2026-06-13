import { useEffect, useState } from "react";
import { socket } from "../socket";

export function useBanner() {
  const [bannerUrl, setBannerUrl] = useState(null);

  useEffect(() => {
    const onAd = ({ url }) => setBannerUrl(url);
    socket.on("ad", onAd);

    return () => socket.off("ad", onAd);
  }, []);

  return bannerUrl;
}