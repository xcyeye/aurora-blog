import {StringUtil} from "@/utils";

export const isLinkExternal = (link: string, base = '/') => {
    // http link or ftp link
    if (isLinkHttp(link) || isLinkFtp(link)) {
        return true;
    }
    // absolute link that does not start with `base`
    if (link.startsWith('/') && !link.startsWith(base)) {
        return true;
    }
    return false;
};

export const isLinkFtp = (link: string | null): boolean => {
    if (!link) {
        return false;
    }
    return  link.startsWith('ftp://')
};

export const isLinkHttp = (link: string | null): boolean => {
    if (!StringUtil.haveLength(link)) return false;
    return /^(https?:)?\/\//.test(link!)
};

export const isLinkMailto = (link: string | null): boolean => {
    if (!link) return false;
    return /^mailto:/.test(link);
}

export const isLinkTel = (link: string | null) => {
    if (!link) return false;
    return /^tel:/.test(link)
}