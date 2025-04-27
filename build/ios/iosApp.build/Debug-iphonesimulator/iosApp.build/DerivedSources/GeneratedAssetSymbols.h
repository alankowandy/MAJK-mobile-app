#import <Foundation/Foundation.h>

#if __has_attribute(swift_private)
#define AC_SWIFT_PRIVATE __attribute__((swift_private))
#else
#define AC_SWIFT_PRIVATE
#endif

/// The "splash_pill_icon" asset catalog image resource.
static NSString * const ACImageNameSplashPillIcon AC_SWIFT_PRIVATE = @"splash_pill_icon";

#undef AC_SWIFT_PRIVATE
